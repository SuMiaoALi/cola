package com.baoyun.ins.mapper.auth.manager;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.manager.Role;
import com.baoyun.ins.entity.auth.manager.Source;

/**
 * @Description: 后台用户持久化
 * @Author cola
 * @Date 2019年12月20日
 */
public interface ManagerUserMapper {

	/**
	 * 获取用户拥有的角色
	 * @param userId
	 * @return
	 */
	@Select("SELECT r.id, r.`name`, r.code FROM t_auth_authorization a, t_auth_role r "
			+ "WHERE a.role_id = r.id AND r.`status` = 1 AND a.user_id= #{userId}")
	List<Role> getRole(String userId);
	
	/**
	 * @Description: 获取权限
	 * @Author cola
	 * @Data: 2019年12月20日
	 * @param id
	 * @return
	 */
	@Select("SELECT t.id, t.`name`, t.code FROM t_auth_authorization a, t_auth_role r, t_auth_resource_assignments s, t_auth_security_resources t "
			+ "WHERE a.role_id = r.id  AND r.id = s.role_id AND s.source_id = t.id  AND a.user_id= #{userId}")
	List<Source> getPermission(String id);

	/**
	 * @Description: 创建用户
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param user
	 */
	@Insert("insert into t_auth_user(id, phone, password, salt, create_time, status, type, name) values (#{id}, #{phone}, #{password}, #{salt}, unix_timestamp(now()), #{status}, #{type}, #{realname}) ")
	void insert(@Valid User user);
	
	/**
	 * @Description: 完善profile
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param user
	 */
	@Insert("insert into t_profile(user_id, sex, name, photo) values (#{id}, '0', #{name}, #{photo}) ")
	void profile(User user);
	
	
	
}
