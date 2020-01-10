package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * @Description: 小程序登录实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("微信小程序登录实体")
@AllArgsConstructor
@Data
public class WxAppletVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("小程序appid")
	private String appid;
	
	@ApiModelProperty("appsecret")
	private String secret;
	
	@ApiModelProperty("授权类型")
	private String grant_type;
	
	@ApiModelProperty("前端返回code")
	private String js_code;

}
