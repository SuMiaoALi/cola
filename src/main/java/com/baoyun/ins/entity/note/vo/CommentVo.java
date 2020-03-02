package com.baoyun.ins.entity.note.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "ID", hidden = true)
	private Long id;
	
	@ApiModelProperty("笔记ID")
	private Long noteId;
	
	@ApiModelProperty("父ID")
	private Long applyId;
	
	@ApiModelProperty(value="内容")
	@NotBlank(message="内容必填")
	@Length(max=140,message="内容不能超过140字")
	private String content;
	
	@ApiModelProperty(value = "评论者", hidden = true)
	private String commenter;
	
	@ApiModelProperty("被回复者id")
	private String applyUser;
	
	@ApiModelProperty(value = "点赞量",hidden=true)
	private Long likeCount;
}
