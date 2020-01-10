package com.baoyun.ins.service.auth.manager.impl;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.mapper.auth.manager.ManagerUserMapper;
import com.baoyun.ins.service.auth.manager.ManagerUserService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.token.PasswordUtil;

/**
 * @Description: 后台用户接口
 * @Author cola
 * @Date 2020年1月3日
 */
@Service
public class ManagerUserServiceImpl implements ManagerUserService {
	
	@Autowired
	private ManagerUserMapper userMapper;

	/**
	 *创建用户
	 */
	@Override
	public Msg<?> create(@Valid User user) {
		// TODO Auto-generated method stub
		String id = UUID.randomUUID().toString().replace("-", "");
		String salt = PasswordUtil.salt();
		String _password = PasswordUtil.hex(user.getPassword(), salt);
		user.setId(id).setPassword(_password).setSalt(salt).setStatus("0").setType("2");
		userMapper.insert(user);
		userMapper.profile(user);
		return new Msg<>();
	}

}
