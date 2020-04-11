package com.baoyun.ins.entity.excel.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExcelTestDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("时间")
	private String time;
	
	@ApiModelProperty("新增病例")
	private Integer newPatient;
	
	@ApiModelProperty("新增死亡")
	private Integer newDeath;
	
	@ApiModelProperty("累计新增病例")
	private Integer totalNewPatient;
	
	@ApiModelProperty("累计新增死亡")
	private Integer totalNewDeath;

}
