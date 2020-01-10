package com.baoyun.ins.entity.bi.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TagMediaQueryDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="id")
	private int id;
	
	@ApiModelProperty(value="标签")
	private String tag;

	@ApiModelProperty(value="父节点")
	private String url;
}
