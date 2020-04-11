package com.baoyun.ins.entity.excel.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: excel测试类
 * @Author cola
 * @Date 2020年3月31日
 */

@ApiModel("excel测试类")
@Data
@Accessors(chain = true)
public class ExcelTestVo implements Serializable {

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

}
