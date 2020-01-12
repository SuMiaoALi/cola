package com.baoyun.ins.mapper.tag;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.bi.dto.TagDto;

/**
 * @Description: 标签接口
 * @Author cola
 * @Date 2020年1月12日
 */
public interface TagMapper {

	/**
	 * @Description: 查询标签列表
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @return
	 */
	@Select("select id, tag, parent_id, weight from t_bi_tag ")
	List<TagDto> list();

	
}
