package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("查询评论入参")
@Data
public class CommentQueryVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("帖子id")
	private Long id;

}
