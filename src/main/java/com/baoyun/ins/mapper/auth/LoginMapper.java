package com.baoyun.ins.mapper.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.vo.FindPwdVo;
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
	 * @Description: 根据openid查询用户
	 * @Author cola
	 * @Data: 2019年12月19日
	 */
	@Select("select u.id, u.status, u.phone, s.nick_name name, s.unionid from t_auth_user u, t_auth_sso s where u.id = s.user_id and s.type = #{1} and s.openid = #{0} limit 1 ")
	User hasUser(String openid, String type);

	/**
	 * @Description: 根据手机号查询用户
	 * @Author cola
	 * @Data: 2020年2月2日
	 * @param phone
	 * @return
	 */
	@Select("select a.id, a.status, a.type, a.salt, a.password, b.name, b.photo from t_auth_user a, t_profile b " + 
			"where a.id = b.user_id and a.phone = #{0} and a.type = 0 limit 1 ")
	User login(String phone);

	/**
	 * @Description: 找回密码
	 * @Author cola
	 * @Data: 2020年2月4日
	 * @param vo
	 * @return
	 */
	@Select("select count(1) from t_auth_user where phone = #{phone} and answer = #{answer} limit 1 ")
	Integer findPwd(FindPwdVo vo);

	/**
	 * @Description: 根据手机号查询颜值
	 * @Author cola
	 * @Data: 2020年2月4日
	 * @param phone
	 * @return
	 */
	@Select("select salt from t_auth_user where phone = #{0} ")
	String salt(String phone);
	

}
