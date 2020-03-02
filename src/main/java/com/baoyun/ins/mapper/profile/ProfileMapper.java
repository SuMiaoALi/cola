package com.baoyun.ins.mapper.profile;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.profile.dto.ProfileDto;
import com.baoyun.ins.entity.profile.vo.ProfileVo;

public interface ProfileMapper {

	/**
	 * @Description: 获取个人信息
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 */
	@Select("select sex, name, photo, description, from_unixtime(birthday, '%Y-%m-%d %H:%i:%s') birthday from t_profile where user_id = #{0} ")
	ProfileDto get(String userId);

	/**
	 * @Description: 修改个人信息
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 */
	@Update({
		"<script>",
			"update t_profile set " + 
			"<if test = 'vo.name != null and vo.name != \"\"'> name = #{vo.name} </if>" + 
			"<if test = 'vo.sex != null and vo.sex != \"\"'> ,sex = #{vo.sex} </if>" + 
			"<if test = 'vo.birthday != null and vo.birthday != \"\"'> , birthday = unix_timestamp('${vo.birthday}') </if>" + 
			"<if test = 'vo.description != null and vo.description != \"\"'> , description = #{vo.description} </if>" +
			"where user_id = #{vo.userId}" +
		"</script>",
	})
	void update(@Param("vo") ProfileVo profileVo);

	/**
	 * @Description: 上传头像
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param file
	 */
	@Update("update t_profile set photo = #{1} where user_id = #{0} ")
	void photo(String userId, String file);
	
	/**
	 * @Description: 查询当前用户发表的文章数母
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 * @return
	 */
	@Select("select count(*) from t_note where author = #{0} ")
	Long notes(String userId);
	
	/**
	 * @Description: 查询当前用户获得的点赞数
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 * @return
	 */
	@Select("select sum(like_count) from t_note where author = #{0} ")
	Long likes(String userId);
	
	/**
	 * @Description: 查询当前用户获得的收藏数
	 * @Author cola
	 * @Data: 2020年2月29日
	 * @param userId
	 * @return
	 */
	@Select("select sum(collection_count) from t_note where author = #{0} ")
	Long collections(String userId);
	
}
