package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @Description: 三方登录实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("三方登录实体")
@Data
@Accessors(chain = true)
public class WxLoginVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("小程序登录code")
	@NotEmpty(message = "登录code不能为空")
	private String code;
	
	@ApiModelProperty("登录渠道") 
	private String channel;
	
	@ApiModelProperty("类型,问答：wxqe")
	private String type;

}
