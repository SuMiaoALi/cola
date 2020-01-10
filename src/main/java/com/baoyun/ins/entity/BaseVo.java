package com.baoyun.ins.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 查询公共分页实体
 * @Author cola
 * @Date 2019年12月19日
 */

@ApiModel("查询公共分页实体")
@Data
public class BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("起始页")
	private Integer start = 1;
	
	@ApiModelProperty("分页大小")
	private Integer pageSize = 20;
	
	@ApiModelProperty("当前用户")
	private String currentUserId;

}
