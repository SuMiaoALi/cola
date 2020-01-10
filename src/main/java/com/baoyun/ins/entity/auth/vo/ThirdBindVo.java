package com.baoyun.ins.entity.auth.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 绑定个人信息实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("绑定个人信息入参")
@Data
@Accessors(chain = true)
public class ThirdBindVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private String userId;
	
	@ApiModelProperty("第三方平台")
	private String type;
	
	@ApiModelProperty("openid")
	private String openid;
	
	@ApiModelProperty("unionid")
	private String unionid;
	
	@ApiModelProperty("第三方平台昵称")
	private String name;
	
	@ApiModelProperty("状态")
	private String status;
	
	@ApiModelProperty("头像")
	private String avatar;
	
	@ApiModelProperty("性别：0-男，1-女")
	private String sex;

}
