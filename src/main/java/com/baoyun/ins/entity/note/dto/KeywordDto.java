package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 用户关键词实体
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("用户关键词实体")
@Data
public class KeywordDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("关键字")
	private String keyword;

}
