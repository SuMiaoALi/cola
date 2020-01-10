package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.note.dto.KeywordDto;
import com.baoyun.ins.entity.note.vo.KeywordQueryVo;


/**
 * @Description: 搜索关键词mapper
 * @Author cola
 * @Date 2020年1月6日
 */
public interface KeywordMapper {

	/**
	 * @Description: 查询用户历史关键词
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param userId
	 * @return
	 */
	@Select("Select keyword from t_keyword where user_id = #{userId} order by id asc")
	List<KeywordDto> list(String userId);

	/**
	 * @Description: 插入用户关键词
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param keyword
	 * @param userId
	 */
	@Insert("insert into t_keyword ( `keyword`, `user_id`) select #{word},#{userId} from dual where not exists (select id from t_keyword where user_id = #{userId} and keyword = #{word} limit 1) ")
	void save(@Param("word")String keyword, @Param("userId")String userId);

	/**
	 * @Description: 删除用户关键词
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param userId
	 * @param keyword
	 */
	@Delete({"<script>",
			"delete from t_keyword where user_id = #{userId} ",
			"<when test='keyword != null and keyword != \"all\"'>and keyword = #{keyword}</when>",
			"</script>"
	})
	void delete(@Param("userId")String userId, @Param("keyword")String keyword);

	/**
	 * @Description: 查询文章标题匹配关键词
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param keywordVo
	 * @return
	 */
	@Select({"<script>",
			 "select title as keyword from t_note " +
			 " WHERE 1=1 ",
			 " <when test = 'key != null and key != \"\"'> and title like '%${key}%' </when> ",
			 " order by keyword asc",
			 "</script>"})
	List<KeywordDto> allKeyword(KeywordQueryVo keywordVo);
}
