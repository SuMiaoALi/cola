package com.baoyun.ins.controller.auth.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.service.auth.manager.ManagerUserService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 后台管理用户接口
 * @Author cola
 * @Date 2020年1月3日
 */

@Api(tags = "后台用户接口")
@RestController
@RequestMapping("/manager/user")
public class ManagerUserController {
	
	@Autowired
	private ManagerUserService managerUserService;
	
	@ApiOperation("创建用户")
	@PostMapping
	public Msg<?> create(@Valid @RequestBody User user) {
		return managerUserService.create(user);
	}

}
