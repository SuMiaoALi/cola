package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 小程序解密实体
 * @Author cola
 * @Date 2019年12月19日
 */
@ApiModel("解密实体")
@Data
public class BindVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("iv")
	@NotEmpty(message = "iv不能为空")
	private String iv;
	
	@ApiModelProperty("openid")
	@NotEmpty(message = "微信openid不能为空")
	private String openid;
	
	@ApiModelProperty("sessionKey")
	@NotEmpty(message = "sessionKey不能为空")
	private String sessionKey;
	
	@ApiModelProperty("encryptedData")
	@NotEmpty(message = "encryptedData不能为空")
	private String encryptedData;

}
