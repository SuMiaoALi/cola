package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * @Description: 关键词查询实体 
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("关键词查询实体")
@Data
public class KeywordQueryVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("关键字")
	private String key;
}
