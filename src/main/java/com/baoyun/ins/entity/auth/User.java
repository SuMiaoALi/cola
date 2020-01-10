package com.baoyun.ins.entity.auth;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 用户实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("用户实体")
@Data
@Accessors(chain = true)
public class User implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("unionid")
	private String unionid; 
	
	@ApiModelProperty("openid")
	private String openid; 
	
	@ApiModelProperty(value = "用户id", hidden = true)
	private String id; 
	
	@ApiModelProperty(hidden = true)
	private String roleId;
	
	@ApiModelProperty("昵称，nickname")
	@Size(max = 20)
	private String name; 
	
	@ApiModelProperty("真实名字，user")
	@Size(max = 20)
	private String realname; 
	
	@ApiModelProperty("手机号")
	@NotNull
	private String phone; 
	
	@ApiModelProperty("密码")
	private String password; 
	
	@ApiModelProperty(value = "加密颜值", hidden = true)
	private String salt; 
	
	@ApiModelProperty("头像")
	private String photo; 
	
	@ApiModelProperty(hidden = true)
	private Long createTime; 
	
	@ApiModelProperty(hidden = true)
	private String type; 
	
	@ApiModelProperty(hidden = true)
	private String status; 

	
}
