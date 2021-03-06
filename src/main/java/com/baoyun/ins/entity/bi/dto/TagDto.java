package com.baoyun.ins.entity.bi.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 标签实体
 * @Author cola
 * @Date 2020年1月12日
 */

@ApiModel("标签实体")
@Data
public class TagDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Integer id;
	
	@ApiModelProperty("标签")
	private String tag;

	@ApiModelProperty("父节点")
	private Integer parentId;
	
	@ApiModelProperty("权重")
	private Integer weight;
	
	@ApiModelProperty("二级标签")
	private List<TagDto> children;
}
