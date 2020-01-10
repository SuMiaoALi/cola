package com.baoyun.ins.entity.qe.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 问题列表实体
 * @Author cola
 * @Date 2019年12月19日
 */
@ApiModel("问题列表实体")
@Data
@Accessors(chain = true)
public class QuestionListDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id") 
	private Integer id;
	
	@ApiModelProperty("问题描述") 
	private String description;
	
	@ApiModelProperty("创建时间") 
	private String createTime;
	
	@ApiModelProperty("处理状态: 0-待回复，1-已回复") 
	private String status;

}
