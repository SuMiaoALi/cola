package com.baoyun.ins.entity.note.dto;

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
@ApiModel("查询帖子下标签")
@Data
@Accessors(chain = true)
public class NoteTagDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("标签")
	private String tag;
	
	@ApiModelProperty("标签ID")
	private Integer tagId;
}
