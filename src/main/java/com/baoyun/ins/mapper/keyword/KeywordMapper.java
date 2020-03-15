package com.baoyun.ins.mapper.keyword;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.keyword.dto.KeywordNoteDto;

/**
 * @Description: 关键词mapper
 * @Author cola
 * @Date 2020年2月28日
 */
public interface KeywordMapper {

	/**
	 * @Description: 保存关键字
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keywordVo
	 */
	@Insert("insert into t_keyword(`user_id`, `keyword`) values (#{0}, #{1}) ")
	void save(String userId, String keyword);

	/**
	 * @Description: 删除关键字
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keywordVo
	 */
	@Delete("delete from t_keyword where user_id = #{0} and keyword = #{1} ")
	void delete(String userId, String keyword);


	/**
	 * @Description: 关键词查询帖子列表
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keywordVo
	 * @return
	 */
	@Select("(select n.id, n.title value, 'N' as type from t_note n " + 
			"where n.title like '%${vo}%' limit 5) " +
			"union " +
			"select p.user_id id, p.name value, 'U' as type from t_profile p " + 
			"where p.name like '%${vo}%' limit 5 "
			)
	List<KeywordNoteDto> listNote(@Param("vo") String keyword);

	
}
