package com.baoyun.ins.entity.auth.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 登录出参实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("登录出参实体")
@Data
@Accessors(chain = true)
public class LoginDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("jwt")
	private String token;
	
	@ApiModelProperty("角色")
	private String role;
	
	@ApiModelProperty("openid")
	private String openid;
	
	@ApiModelProperty("sessionKey")
	private String session;
	
	private String version = "1.0";
	
	private String code = "1.0";

}
