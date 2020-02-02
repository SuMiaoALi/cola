package com.baoyun.ins.mapper.auth.manager;

import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.auth.User;

/**
 * @Description: 后台登录持久化
 * @Author cola
 * @Date 2019年12月20日
 */
public interface ManagerLoginMapper {

	/**
	 * @Description: 后台登录:2-后台管理员
	 * @Author cola
	 * @Data: 2019年12月20日
	 * @param phone
	 * @return
	 */
	@Select("select a.id, a.status, a.type, a.salt, a.password, b.name, b.photo from t_auth_user a, t_profile b " + 
			"where a.id = b.user_id and a.phone = #{0} and a.type = 2 limit 1 ")
	User login(String phone);

	
}
