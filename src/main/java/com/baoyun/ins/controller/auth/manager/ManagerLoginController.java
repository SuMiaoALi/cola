package com.baoyun.ins.controller.auth.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.auth.manager.vo.Login;
import com.baoyun.ins.service.auth.manager.ManagerLoginService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 
 * @Author cola
 * @Date 2019年12月20日
 */

@Api(tags = "后台登录接口")
@RequestMapping("/manager/login")
@RestController
public class ManagerLoginController {

	@Autowired
	private ManagerLoginService loginService;
	
	@ApiOperation("用户登录")
	@PostMapping
	public Msg<?> login(@Valid @RequestBody Login login) {
		return loginService.login(login);
	}
}
