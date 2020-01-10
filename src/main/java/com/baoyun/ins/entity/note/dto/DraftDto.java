package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DraftDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="id")
	private int id;
	
	@ApiModelProperty(value="标题")
	private String title;
	
	@ApiModelProperty(value="内容")
	private String content;
	
	@ApiModelProperty(value="封面")
	private String cover;
	
	@ApiModelProperty(value="标签")
	private String tags;

}
