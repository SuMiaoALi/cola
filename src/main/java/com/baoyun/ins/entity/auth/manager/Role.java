package com.baoyun.ins.entity.auth.manager;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 角色实体
 * @Author cola
 * @Date 2019年12月20日
 */

@ApiModel("角色实体")
@Data
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("角色id")
	private Integer id; 
	
	@ApiModelProperty("名称")
	@NotNull
	private String name; 
	
	@ApiModelProperty("代码")
	@NotNull
	@Size(max = 20,min = 1)
	private String code; 
	
	@ApiModelProperty("状态,1-正常")
	private String status; 
	
	@ApiModelProperty("类型")
	private String type; 
	
	@ApiModelProperty("权限id ,分割")
	private String sourceIds; 
	
}
