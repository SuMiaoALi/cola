package com.baoyun.ins.controller.auth;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.auth.vo.BindVo;
import com.baoyun.ins.entity.auth.vo.WxLoginVo;
import com.baoyun.ins.service.auth.LoginService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 登录接口
 * @Author cola
 * @Date 2019年12月19日
 */

@Api(tags = "登录接口")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@ApiOperation("微信小程序登录")
	@GetMapping("/wx")
	public Msg<?> wxAppletLogin(@Valid  WxLoginVo wxLogin) {
		return loginService.wxAppletLogin(wxLogin);
	}
	
	@ApiOperation("微信小程序获取用户信息")
	@PostMapping("/wx/userinfo")
	public Msg<Object> initWxAppletUserInfo(@Valid @RequestBody BindVo bind) {
		return loginService.getWxAppUserInfo(bind);
	}
	
	@ApiOperation("微信小程序绑定")
	@PostMapping("/wx/bind")
	public Msg<Object> bind(@Valid @RequestBody BindVo bind) {
		return loginService.bindWxApp(bind);
	}
	
	
}
