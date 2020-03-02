package com.baoyun.ins.entity.keyword.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("关键词查询帖子实体")
@Data
public class KeywordNoteDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("帖子id")
	private String id;
	
	@ApiModelProperty("帖子标题")
	private String title;
	
	@ApiModelProperty("类型，帖子=N，人=U")
	private String type;
	
}
