package com.baoyun.ins.entity.profile.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 个人信息实体
 * @Author cola
 * @Date 2020年2月29日
 */

@ApiModel("个人信息实体")
@Data
@Accessors(chain = true)
public class ProfileDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty("性别")
	private String sex;
	
	@ApiModelProperty("生日")
	private String birthday;
	
	@ApiModelProperty("个人简介")
	private String description;
	
	@ApiModelProperty("头像")
	private String photo;
	
	@ApiModelProperty("帖子数")
	private Long notes;
	
	@ApiModelProperty("点赞数")
	private Long likes;
	
	@ApiModelProperty("收藏数")
	private Long collections;

}
