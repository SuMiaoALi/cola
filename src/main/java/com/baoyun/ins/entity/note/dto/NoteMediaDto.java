package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 帖子图片实体
 * @Author cola
 * @Date 2020年2月23日
 */

@ApiModel("帖子图片实体")
@Data
public class NoteMediaDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("图片地址")
	private String url;
	
}
