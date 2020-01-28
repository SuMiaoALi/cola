package com.baoyun.ins.service.auth.manager.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.manager.Role;
import com.baoyun.ins.entity.auth.manager.Source;
import com.baoyun.ins.entity.auth.manager.vo.Login;
import com.baoyun.ins.mapper.auth.manager.ManagerLoginMapper;
import com.baoyun.ins.mapper.auth.manager.ManagerUserMapper;
import com.baoyun.ins.service.auth.manager.ManagerLoginService;
import com.baoyun.ins.utils.json.GlobalReturnCode;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.string.StringUtil;
import com.baoyun.ins.utils.token.PasswordUtil;
import com.baoyun.ins.utils.token.TokenUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 后台登录
 * @Author cola
 * @Date 2019年12月20日
 */

@Slf4j
@Service
public class ManagerLoginServiceImpl implements ManagerLoginService {
	
	@Autowired
	private ManagerLoginMapper loginMapperl;
	
	@Autowired
	private ManagerUserMapper userMapper;

	/**
	 * 后台登录
	 */
	@Override
	public Msg<Object> login(Login login) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<Object>();
		User user = loginMapperl.login(login.getPhone());
		// 用户存在
		if (user != null && StringUtil.isNotNullOrEmpty(user.getId())) {
			// 用户禁用
			if (!"0".equals(user.getStatus())) {
				msg.setCode(GlobalReturnCode.AUTH_DISABLE);
				msg.setMessage("用户无权限");
			}
			// 密码登录
			if ("passwd".equals(login.getScope())) {
				String _password = PasswordUtil.hex(login.getPassword(), user.getSalt());
				if (!_password.equals(user.getPassword())) {
					msg.setCode(GlobalReturnCode.SECRET_ERROR);
					msg.setMessage("密码错误");
				}
			}
			log.info("登录成功");
			
			// 用户角色
			List<Role> roles = userMapper.getRole(user.getId());
			Set<String> scopes = new HashSet<>();
			roles.stream().forEach(item -> scopes.add(item.getCode()));
			
			// 权限
			List<Source> sources = userMapper.getPermission(user.getId());
			Set<String> privileges = new HashSet<String>();
			sources.stream().forEach(item -> privileges.add(item.getCode()));
			
			// token
			String token = TokenUtil.createToken(user.getId(), scopes, privileges);
			msg.setData(new HashedMap().put("token", token));
		} else {
			// 用户不存在
			msg.setCode(GlobalReturnCode.USER_NOT_EXIT);
			msg.setMessage("用户不存在");
		}
		return msg;
	}

}
