package com.baoyun.ins.entity.monitor.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InformDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="id")
	private int id;
	
	@ApiModelProperty(value="标题")
	private String title;
	
	@ApiModelProperty(value="描述")
	private String description;
	
	@ApiModelProperty(value="父ID")
	private int parentId;
	
	@ApiModelProperty(value="子项")
	private List<InformDto> children;

}
