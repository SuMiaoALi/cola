package com.baoyun.ins.entity.news.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 获取token实体
 * @Author cola
 * @Date 2020年1月13日
 */

@ApiModel("获取token入参实体")
@Data
@Accessors(chain = true)
public class GetTokenVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("平台: 微信-WX，头条-TT，百度-BD")
	private String client;
	
	@ApiModelProperty("产品类型: rryPublic, rryMini, qeMini; rry, qe; office")
	private String type;
	
	@ApiModelProperty("消息类型：订阅=WX_SUBSCRIBE，模板-WX_MODEL,TT_MODEL,客服-BD_KEFU")
	private String msgType;
	
}
