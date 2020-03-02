package com.baoyun.ins.entity.profile.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @Description: 保存个人信息实体
 * @Author cola
 * @Date 2020年2月29日
 */

@ApiModel("保存个人信息")
@Data
public class ProfileVo implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户id", hidden = true)
	private String userId;
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty("性别")
	private String sex;
	
	@ApiModelProperty("生日")
	private String birthday;
	
	@ApiModelProperty("个人简介")
	private String description;
	
}
