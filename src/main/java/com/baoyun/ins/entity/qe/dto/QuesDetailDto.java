package com.baoyun.ins.entity.qe.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("问题详情实体")
@Data
@Accessors(chain = true)
public class QuesDetailDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("问题id") 
	private Integer id;
	
	@ApiModelProperty("性别：0-男，1-女") 
	private String sex;
	
	@ApiModelProperty("年龄") 
	private String age;
	
	@ApiModelProperty("阶段") 
	private String stage;
	
	@ApiModelProperty("状态") 
	private String status;
	
	@ApiModelProperty("问题描述") 
	private String description;
	
	@ApiModelProperty("创建时间") 
	private String createTime;
	
	@ApiModelProperty("答案") 
	private String answer;

}
