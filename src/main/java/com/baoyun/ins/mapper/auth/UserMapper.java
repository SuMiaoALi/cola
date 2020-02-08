package com.baoyun.ins.mapper.auth;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.auth.User;
import com.baoyun.ins.entity.auth.vo.SignUpVo;
import com.baoyun.ins.entity.auth.vo.ThirdBindVo;

/**
 * @Description: 用户持久化
 * @Author cola
 * @Date 2019年12月19日
 */
public interface UserMapper {

	/**
	 * @Description: 用户注册
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param user
	 */
	@Insert("insert into t_auth_user(id, phone, password, salt, create_time, status, type) values (#{id}, #{phone}, #{password}, #{salt}, #{createTime}, #{status}, #{type}) ")
	void insert(User user);
	
	/**
	 * @Description: web用户注册
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param user
	 */
	@Insert("insert into t_auth_user(id, phone, password, salt, create_time, status, type, answer) values (#{id}, #{phone}, #{password}, #{salt}, unix_timestamp(now()), #{status}, #{type}, #{answer} ) ")
	void insertWeb(SignUpVo signUp);
	
	/**
	 * @Description: web完善个人信息
	 * @Author cola
	 * @Data: 2019年12月19日
	 */
	@Insert("insert into `t_profile`(`user_id`, `sex`, `name`, `photo`) values (#{id}, #{sex}, #{name}, #{photo}) ")
	void profileWeb(SignUpVo signUp);

	/**
	 * @Description: 完善个人信息
	 * @Author cola
	 * @Data: 2019年12月19日
	 */
	@Insert("insert into `t_profile`(`user_id`, `sex`, `name`, `photo`) values (#{userId}, #{sex}, #{name}, #{avatar}) ")
	void profile(ThirdBindVo bind);

	/**
	 * @Description: 修改手机号
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param userId
	 * @param phone
	 */
	@Update("update t_auth_user set phone = #{1} where id = #{0}")
	void phone(String userId, String phone);

	/**
	 * @Description: 完善sso个人信息
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param bindVo
	 */
	@Update("update t_auth_sso set unionid = #{unionid}, nick_name = #{name}, avatar = #{avatar} where user_id = #{userId} ")
	void sso(ThirdBindVo bindVo);
	
	/**
	 * @Description: 修改用户信息
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param user
	 */
	@Update({
		"<script>" +
		"update t_auth_user set 1 = 1" +
		"<if test = 'vo.phone != null and vo.phone != \"\"'> , phone = #{vo.phone} </if>" +
		"<if test = 'vo.status != null and vo.status != \"\"'> , status = #{vo.status} </if>" +
		"<if test = 'vo.type != null and vo.type != \"\"'> , type = #{vo.type} </if>" +
		"</script>"
	})
	void update(@Param("vo") User user);

	/**
	 * @Description: 修改密码
	 * @Author cola
	 * @Data: 2020年2月4日
	 * @param vo
	 */
	@Update("update t_auth_user set password = #{1} where phone = #{0} ")
	void updatePwd(String phone, String password);

}
