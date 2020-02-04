package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 找回密码实体
 * @Author cola
 * @Date 2020年2月4日
 */

@ApiModel("找回密码实体")
@Data
@Accessors(chain = true)
public class FindPwdVo implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("手机号")
	private String phone;

	@ApiModelProperty("答案")
	private String answer;
	
	@ApiModelProperty("新密码")
	private String password;
}
