package com.baoyun.ins.mapper.tag.manager;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.bi.dto.TagDto;
import com.baoyun.ins.entity.tag.vo.TagVo;

public interface ManagerTagMapper {

	/**
	 * @Description: 查询标签列表
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @return
	 */
	@Select("select id, tag, parent_id parentId, weight from t_bi_tag order by weight desc ")
	List<TagDto> list();

	/**
	 * @Description: 增加标签 
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param tagVo
	 * @return
	 */
	@Insert("insert into t_bi_tag(tag, parent_id, weight) values (#{tag}, #{parentId}, #{weight})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	void insert(TagVo tagVo);

	/**
	 * @Description: 删除标签
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param id
	 */
	@Delete("delete from t_bi_tag where id = #{0} ")
	void delete(Integer id);

	/**
	 * @Description: 修改标签
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param tagVo
	 */
	@Update("update t_bi_tag set tag = #{tag}, parent_id = #{parentId}, weight = #{weight} ")
	void update(TagVo tagVo);

}
