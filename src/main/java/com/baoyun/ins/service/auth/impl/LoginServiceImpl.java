package com.baoyun.ins.service.auth.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baoyun.ins.config.constant.C;
import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.dto.LoginDto;
import com.baoyun.ins.entity.auth.vo.BindVo;
import com.baoyun.ins.entity.auth.vo.ThirdBindVo;
import com.baoyun.ins.entity.auth.vo.WxLoginVo;
import com.baoyun.ins.mapper.auth.LoginMapper;
import com.baoyun.ins.mapper.auth.UserMapper;
import com.baoyun.ins.service.auth.LoginService;
import com.baoyun.ins.utils.http.HttpClientUtils;
import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.string.WXCore;
import com.baoyun.ins.utils.token.PasswordUtil;
import com.baoyun.ins.utils.token.TokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 登录接口
 * @Author cola
 * @Date 2019年12月19日
 */

@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
	
	@Value("${wx.qe.appid}")
	private String wxqeAppId;
	
	@Value("${wx.qe.appsecret}")
	private String wxqeAppSecret;
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private UserMapper userMapper;

	/**
	 * 微信小程序登录
	 */
	@Override
	public Msg<?> wxAppletLogin(@Valid WxLoginVo wxLogin) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxqeAppId + "&secret=" 
					 + wxqeAppSecret + "&js_code=" + wxLogin.getCode() + "&grant_type=authorization_code";
		
		String callback = null;
		try {
			callback = HttpClientUtils.sendPost(url);
			// 解析返回的jsonstr
			JSONObject json = JSONObject.parseObject(callback);
			log.info(json.toString());
			
			if (!json.containsKey("openid")) {
				// 请求失败
				msg.setCode(json.get("errcode").toString());
				msg.setData(json.get("errmsg"));
			} else {
				// 返回给前端的实体:token，role，openid，sessionKey
				LoginDto loginDto = new LoginDto();
				loginDto.setOpenid(json.get("openid").toString());
				loginDto.setSession(json.get("session_key").toString());
				
				// 根据openid查询是否有用户
				User user = loginMapper.hasUser(loginDto.getOpenid(), "wxqe");
				Set<String> scopes = new HashSet<>();
				
				// 用户存在检查状态
				if (user != null && StringUtil.isNotNullOrEmpty(user.getId())) {
					if (!"0".equals(user.getStatus())) {
						msg.setCode(GlobalReturnCode.AUTH_DISABLE);
						return msg;
					}
				} else {
					// 用户不存在
					ThirdBindVo bind = new ThirdBindVo();
					bind.setOpenid(loginDto.getOpenid()).setType(wxLogin.getType());
					
					// 注册绑定
					user = this.signUp(bind);
					log.info("新用户：" + user);
				}
				
				// 自定义登录角色: 个人信息-sso.unionid
				System.out.println("用户：" + user);
				if (C.USER.LOGIN.WXQE.equals(wxLogin.getType()) && StringUtil.isNotNullOrEmpty(user.getUnionid())) {
					scopes.add(C.USER.TYPE.USER);
					loginDto.setRole(C.USER.TYPE.USER);
				} else {
					// 游客，必须授权个人信息
					scopes.add(C.USER.TYPE.TOURIST);
					loginDto.setRole(C.USER.TYPE.TOURIST);
				}
				// 自定义登录令牌
				String token = TokenUtil.createToken(user.getId(), scopes, null);
				loginDto.setToken(token);
				
				msg.setData(loginDto);
				return msg;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 解密微信小程序用户信息
	 */
	@Override
	public Msg<Object> getWxAppUserInfo(@Valid BindVo bind) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		// 堆成解密数据
		net.sf.json.JSONObject JSONObject = WXCore.decrypt(wxqeAppId, bind.getEncryptedData(), bind.getSessionKey(), bind.getIv());
		
		if (JSONObject == null) {
			msg.setCode(GlobalReturnCode.PARAM_ERROR);
			return msg;
		}
		// 微信：1-男，2-女，转换成：0-男，1-女
		int gender = JSONObject.getInt("gender");
		String sex = "";
		if (gender == 1) {
			sex = "0";
		} else {
			sex = "1";
		}
		ThirdBindVo bindVo = new ThirdBindVo();
		bindVo.setOpenid(JSONObject.getString("openid")).setUnionid(JSONObject.getString("unionid") == null ? "" : JSONObject.getString("unionid"))
			  .setAvatar(JSONObject.getString("avatarUrl")).setName(JSONObject.getString("nickName")).setSex(sex);
		// 查询用户
		String type = "wxqe";
		User user = loginMapper.hasUser(bindVo.getOpenid(), type);
		
		if (user != null && StringUtil.isNotNullOrEmpty(user.getId())) {
			// 完善信息
			userMapper.profile(bindVo);
			userMapper.sso(bindVo);
		} else {
			// 注册绑定
			User _user = this.signUp(bindVo);
			bindVo.setUnionid(_user.getId());
			userMapper.profile(bindVo);
		}
		return msg;
	}

	/**
	 *绑定微信小程序手机号
	 */
	@Override
	public Msg<Object> bindWxApp(@Valid BindVo bind) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		// 堆成解密数据
		net.sf.json.JSONObject JSONObject = WXCore.decrypt(wxqeAppId, bind.getEncryptedData(), bind.getSessionKey(), bind.getIv());
		
		if (JSONObject == null) {
			msg.setCode(GlobalReturnCode.PARAM_ERROR);
			return msg;
		}
		String phone = JSONObject.getString("phoneNumber");
		String openid = bind.getOpenid();
		String type = "wxqe";
		
		User user = loginMapper.hasUser(openid, type);
		if (user != null && StringUtil.isNotNullOrEmpty(user.getId())) {
			user.setPhone(phone);
			userMapper.update(user);
		} else {
			// 注册绑定
			msg.setCode(GlobalReturnCode.USER_NOT_EXIT);
		}
		return msg;
	}
	
	/**
	 * 注册用户，绑定sso
	*/
	private User signUp(ThirdBindVo bind) {
		User user = new User();
		// 注册账号
		String salt = PasswordUtil.salt();
		user.setId(UUID.randomUUID().toString().replaceAll("-", "")).setCreateTime(System.currentTimeMillis()/1000).setStatus("0")
			.setType("0").setSalt(salt).setPassword(PasswordUtil.hex(UUID.randomUUID().toString(), salt));
		userMapper.insert(user);
		// 插入的id
		String userId = user.getId();
		log.info("生成deid：" + userId);
		// 绑定sso
		bind.setUserId(userId);
		bind.setStatus("0");
		loginMapper.thdBind(bind);
		return user;
	}
	
}