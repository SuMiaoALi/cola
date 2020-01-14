package com.baoyun.ins.entity.news.vo;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 小程序模板消息实体
 * @Author cola
 * @Date 2020年1月12日
 */
@ApiModel("小程序模板消息实体")
@Data
@Accessors(chain = true)
public class MiniTemplateVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("接受者openid")
	private String touser;
	
	@ApiModelProperty("模板id")
	private String template_id;
	
	@ApiModelProperty("模板跳转链接")
	private String page; //非必须
	
	@ApiModelProperty("data")
	private Map<String, MiniTemplateDataVo> data;
	
}
