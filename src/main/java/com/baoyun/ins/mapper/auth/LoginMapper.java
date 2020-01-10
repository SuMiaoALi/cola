package com.baoyun.ins.mapper.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.vo.ThirdBindVo;

/**
 * @Description: 登录持久化
 * @Author cola
 * @Date 2019年12月19日
 */
public interface LoginMapper {

	/**
	 * @Description: 绑定sso
	 * @Author cola
	 * @Data: 2019年12月19日
	 */
	@Insert("insert into t_auth_sso(user_id, type, openid, unionid, nick_name, status, create_time, avatar) values(#{userId}, #{type}, #{openid}, #{unionid}, #{name}, #{status}, unix_timestamp(now()), #{avatar}) ")
	void thdBind(ThirdBindVo bind);

	/**
	 * @Description: 根据openid查询是否有用户
	 * @Author cola
	 * @Data: 2019年12月19日
	 */
	@Select("select u.id, u.status, u.phone, s.nick_name name, s.unionid from t_auth_user u, t_auth_sso s where u.id = s.user_id and s.type = #{1} and s.openid = #{0} limit 1 ")
	User hasUser(String openid, String type);
	

}
