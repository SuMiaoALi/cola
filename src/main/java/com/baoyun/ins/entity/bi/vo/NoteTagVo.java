package com.baoyun.ins.entity.bi.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 帖子标签实体
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("帖子标签实体")
@Data
@Accessors(chain = true)
public class NoteTagVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("标签名")
	private String tag;
	
	@ApiModelProperty("标签ID")
	private Integer id;
	
	@ApiModelProperty(value = "父标签ID", hidden = true)
	private Integer parentId;
	
	@ApiModelProperty(value = "帖子ID", hidden = true)
	private Integer noteId;
	
}
