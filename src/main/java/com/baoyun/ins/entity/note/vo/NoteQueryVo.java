package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 笔记列表查询实体
 * @Author cola
 * @Date 2020年1月3日
 */

@ApiModel("笔记列表查询实体")
@Data
@Accessors(chain = true)
public class NoteQueryVo extends BaseVo{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("搜索字段")
	private String key;
	
	@ApiModelProperty("标签ID")
	private String tagId;
	
	@ApiModelProperty(value = "用户ID", hidden = true)
	private String userId;
	
	@ApiModelProperty(value = "作者", hidden = true)
	private String author;

	@ApiModelProperty(value = "0待审核 1审核通过 2审核不通过", hidden = true)
	private String status;
	
	@ApiModelProperty(value = "收藏者", hidden = true)
	private String collector;
	
	
}
