package com.baoyun.ins.entity.qe.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel("保村问卷入参")
@Data
@Accessors(chain = true)
public class QuesVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(hidden = true)
	private Integer id;
	
	@ApiModelProperty("性别：0-男，1-女")
	private String sex;
	
	@ApiModelProperty("年龄")
	private String age;
	
	@ApiModelProperty("阶段")
	@Size(max = 100)
	private String stage;
	
	@ApiModelProperty("问题描述")
	@NotNull
	private String description;
	
	@ApiModelProperty("图片url，逗号分隔")
	private String picture;

	@ApiModelProperty(value = "用户id", hidden = true)
	private String userId;
	
	@ApiModelProperty("状态；0-待处理，1-已处理，-1-已作废")
	private String status;
	
	@ApiModelProperty("视频id")
	private Integer batchId;
	
	@ApiModelProperty("模板formId")
	private String formId;
	
}

