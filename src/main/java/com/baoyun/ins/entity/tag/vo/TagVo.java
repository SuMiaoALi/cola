package com.baoyun.ins.entity.tag.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 后台标签管理保存实体
 * @Author cola
 * @Date 2020年1月12日
 */

@ApiModel("后台标签管理保存实体")
@Data
@Accessors(chain = true)
public class TagVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "id", hidden = true)
	private Integer id;
	
	@ApiModelProperty("标签名")
	private String tag;
	
	@ApiModelProperty("父id")
	private Integer parentId;
	
	@ApiModelProperty("权重")
	private Integer weight;

}
