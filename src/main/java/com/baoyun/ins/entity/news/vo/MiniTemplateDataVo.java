package com.baoyun.ins.entity.news.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 小程序模板消息data实体
 * @Author cola
 * @Date 2020年1月12日
 */
@ApiModel("小程序模板消息data实体")
@Data
public class MiniTemplateDataVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("内容")
	private String value;
	
	public MiniTemplateDataVo(String value) {
		this.value = value;
	}
	
}
