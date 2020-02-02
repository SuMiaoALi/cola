package com.baoyun.ins.service.auth;

import javax.validation.Valid;

import com.baoyun.ins.entity.auth.manager.vo.Login;
import com.baoyun.ins.entity.auth.vo.BindVo;
import com.baoyun.ins.entity.auth.vo.SignUpVo;
import com.baoyun.ins.entity.auth.vo.WxLoginVo;
import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 登录接口
 * @Author cola
 * @Date 2019年12月19日
 */

public interface LoginService {

	/**
	 * @Description: 微信小程序登录 
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param wxLogin
	 * @return
	 * @throws 
	 */
	Msg<?> wxAppletLogin(@Valid WxLoginVo wxLogin);

	/**
	 * @Description: 解密微信小程序用户信息
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param bind
	 * @return
	 * @throws 
	 */
	Msg<Object> getWxAppUserInfo(@Valid BindVo bind);

	/**
	 * @Description: 绑定微信小程序手机号
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param bind
	 * @return
	 * @throws 
	 */
	Msg<Object> bindWxApp(@Valid BindVo bind);

	/**
	 * @Description: web注册
	 * @Author cola
	 * @Data: 2020年2月2日
	 * @param signUp
	 * @return
	 */
	Msg<?> webSignUp(SignUpVo signUp);

	/**
	 * @Description: web登录
	 * @Author cola
	 * @Data: 2020年2月2日
	 * @param login
	 * @return
	 */
	Msg<?> webSignIn(Login login);

	
}
