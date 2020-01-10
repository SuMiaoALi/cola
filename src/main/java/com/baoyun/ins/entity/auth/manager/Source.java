package com.baoyun.ins.entity.auth.manager;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 权限实体
 * @Author cola
 * @Date 2019年12月20日
 */

@ApiModel("权限实体")
@Data
public class Source implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Integer id;
	
	@ApiModelProperty("名称")
	private String name;
	
	@ApiModelProperty("代码")
	private String code;
	
	@ApiModelProperty("类型")
	private String type;
	
	@ApiModelProperty("状态")
	private String status;
	
	@ApiModelProperty("父权限ID")
	private Integer parentId;
	
	@ApiModelProperty("父权限名字")
	private String parentName;
	
	@ApiModelProperty("孩子")
	private List<Source> children;

}
