package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DraftQueryDto implements Serializable {
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
	
	@ApiModelProperty(value="更新时间")
	private String time;

}
