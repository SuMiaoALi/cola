package com.baoyun.ins.entity.keyword.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 关键词查询帖子实体
 * @Author cola
 * @Date 2020年2月28日
 */

@ApiModel("关键词查询帖子实体")
@Data
@Accessors(chain = true)
public class NoteByKeywordVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("关键词")
	private String keyword;
	
}
