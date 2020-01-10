package com.baoyun.ins.entity.auth.manager.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("后台登录实体")
@Data
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("账号")
	@NotNull
	private String phone;
	
	@ApiModelProperty("密码")
	private String password; //可为密码/动态密码
	
	@ApiModelProperty("作用域:账号密码登录：passwd；动态密码登录：sms")
	private String scope;

}
