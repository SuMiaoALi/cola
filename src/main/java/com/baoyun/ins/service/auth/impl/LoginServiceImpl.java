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
import com.aliyuncs.utils.StringUtils;
import com.baoyun.ins.config.constant.C;
import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.dto.LoginDto;
import com.baoyun.ins.entity.auth.manager.vo.Login;
import com.baoyun.ins.entity.auth.vo.BindVo;
import com.baoyun.ins.entity.auth.vo.FindPwdVo;
import com.baoyun.ins.entity.auth.vo.SignUpVo;
import com.baoyun.ins.entity.auth.vo.ThirdBindVo;
import com.baoyun.ins.entity.auth.vo.WxLoginVo;
import com.baoyun.ins.mapper.auth.LoginMapper;
import com.baoyun.ins.mapper.auth.UserMapper;
import com.baoyun.ins.service.auth.LoginService;
import com.baoyun.ins.utils.ali.SmsUtil;
import com.baoyun.ins.utils.http.HttpClientUtils;
import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.redis.RedisConstant;
import com.baoyun.ins.utils.redis.RedisUtil;
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
	
	@Autowired
	private SmsUtil smsUtil;
	
	@Autowired
	private RedisUtil redisUtil;

	// 阿里key
	@Value("${ali.accessKeyId}")
	private String accessKeyId;
	
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
		// 注册sso
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

	/**
	 *web用户注册完善信息
	 */
	@Override
	public Msg<?> webSignUp(SignUpVo signUp) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<>();
		String phone = signUp.getPhone();
		String isExists = loginMapper.salt(phone);
	    // 说明手机号已被注册
		if (StringUtil.isNotNullOrEmpty(isExists)) {
			msg.setCode(GlobalReturnCode.PROFILE_PHONE_EXIT);
			msg.setMessage("手机号已注册");
			return msg;
		}
		String code = signUp.getCode();
		// 发送出去的code
		try {
			if (redisUtil.exists(RedisConstant.SMS + phone)) {
				String _code = redisUtil.get(RedisConstant.SMS + phone, String.class);
				// 验证码错误
				if (!code.equals(_code)) {
					msg.setCode(GlobalReturnCode.SMS_CODE_ERROR);
					msg.setMessage("验证码错误");
					return msg;
				}
				String salt = PasswordUtil.salt();
				// 密码加密
				String _password = PasswordUtil.hex(signUp.getPassword(), salt);
				signUp.setSalt(salt).setType("0")
					  .setId(UUID.randomUUID().toString().replaceAll("-", ""))
					  .setStatus("0").setPassword(_password);
				userMapper.insertWeb(signUp);
				userMapper.profileWeb(signUp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg.setCode(GlobalReturnCode.REDIS_ERROR);
			msg.setMessage("Redis服务器连接不上了mmp，我回头教训它，请重试一下噢");
			return msg;
		} 
		return msg;
	}

	/**
	 *web登录
	 */
	@Override
	public Msg<?> webSignIn(Login login) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		// JWT
		LoginDto loginDto = new LoginDto();
		// 登录渠道
		String channel = login.getScope();
		String phone = login.getPhone();
		
		User user = loginMapper.login(phone);
		// 用户存在
		if (user != null && StringUtil.isNotNullOrEmpty(user.getId())) {
			// 1、判断禁用状态
			if (!"0".equals(user.getStatus())) {
				msg.setCode(GlobalReturnCode.AUTH_DISABLE);
				msg.setMessage("用户无权限");
				return msg;
			}
			
			// 2、密码登录
			if ("passwd".equals(channel)) {
				String _password = PasswordUtil.hex(login.getPassword(), user.getSalt());
				if (!_password.equals(user.getPassword())) {
					msg.setCode(GlobalReturnCode.SECRET_ERROR);
					msg.setMessage("密码错误");
					return msg;
				}
			}
			
			// 3、验证码登录
			if ("sms".equals(channel)) {
				try {
					if (redisUtil.exists(RedisConstant.SMS + phone)) {
						// 验证码
						String code = redisUtil.get(RedisConstant.SMS + phone, String.class);
						if (login.getPassword().equals(code)) {
							// 登录成功
							msg.setCode("10000");
							msg.setMessage("登录成功");
						} else {
							msg.setCode(GlobalReturnCode.SMS_CODE_ERROR);
							msg.setMessage("验证码错误");
							return msg;
						}
					} 
				} catch (Exception e) {
					msg.setCode(GlobalReturnCode.REDIS_ERROR);
					msg.setMessage("Redis服务器连接不上了mmp，我回头教训它，请重试一下噢");
					return msg;
				}
			}			
			log.info("登录成功");
			
			Set<String> scopes = new HashSet<>();
			scopes.add(C.USER.TYPE.USER);
			
			String token = TokenUtil.createToken(user.getId(), scopes, null);
			loginDto.setRole(C.USER.TYPE.USER).setToken(token).setName(user.getName()).setPhoto(user.getPhoto());
			msg.setData(loginDto);
			
		} else {
			msg.setCode(GlobalReturnCode.USER_NOT_EXIT);
			msg.setMessage("用户不存在");
		}
		return msg;
	}

	/**
	 * 找回密码（密保式）
	 */
	@Override
	public Msg<?> webFind(FindPwdVo vo) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<>();
		Integer i = loginMapper.findPwd(vo);
		if (i > 0) {
			// 密保正确
			String phone = vo.getPhone();
			String salt = loginMapper.salt(phone);
			// 生成新密码
			String newPassword = PasswordUtil.hex(vo.getPassword(), salt);
			userMapper.updatePwd(phone, newPassword);
			msg.setCode("10000").setMessage("修改成功");
		} else {
			msg.setCode("30002").setMessage("修改失败");
		}
		return msg;
	}

	/**
	 * 发送验证码
	 */
	@Override
	public Msg<?> sendVreifyCode(String phone) {
		// TODO Auto-generated method stub
		Msg<?> msg = new Msg<>();
		log.info(phone);
		if (StringUtil.isNotNullOrEmpty(phone)) {
			msg = smsUtil.sendSms(phone);
		} else {
			msg.setCode(GlobalReturnCode.OPERA_FAILURE);
			msg.setMessage("操作失败");
		}
		return msg;
	}
	
}
