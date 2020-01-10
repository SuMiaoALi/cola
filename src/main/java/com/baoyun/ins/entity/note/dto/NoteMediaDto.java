package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteMediaDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="图片地址")
	private String url;
	
	@ApiModelProperty(value="图片类型:0用户 1图库")
	private String type;
	
}
