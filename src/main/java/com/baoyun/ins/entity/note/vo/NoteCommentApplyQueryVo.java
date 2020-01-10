package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteCommentApplyQueryVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="id")
	private long id;
	
	
	@ApiModelProperty(value="笔记ID")
	private long noteId;
	
	@ApiModelProperty(value="评论ID")
	private long commentId;

}
