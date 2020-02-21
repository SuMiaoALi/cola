package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 注册用户实体
 * @Author cola
 * @Date 2020年2月2日
 */

@ApiModel("注册用户实体")
@Data
@Accessors(chain = true)
public class SignUpVo implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户id", hidden = true)
	private String id;
	
	@ApiModelProperty("手机号")
	@NotNull
	private String phone; 
	
	@ApiModelProperty("密码")
	private String password; 
	
	@ApiModelProperty("头像")
	private String photo; 
	
	@ApiModelProperty("0-男，1-女")
	private String sex; 
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty(value = "加密颜值", hidden = true)
	private String salt; 
	
	@ApiModelProperty(hidden = true)
	private String type; 
	
	@ApiModelProperty(hidden = true)
	private String status; 
	
	@ApiModelProperty("验证码")
	private String code;

}
