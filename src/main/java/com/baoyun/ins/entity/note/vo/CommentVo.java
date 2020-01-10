package com.baoyun.ins.entity.note.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommentVo {
	
	@ApiModelProperty(value="ID",hidden=true)
	private Long id;
	
	@ApiModelProperty(value="笔记ID")
	private Long noteId;
	
	@ApiModelProperty(value="评论其他的评论ID")
	private String applyId;
	
	@ApiModelProperty(value="内容")
	@NotBlank(message="内容必填")
	@Length(max=140,message="内容不能超过140字")
	private String content;
	
	@ApiModelProperty(value="评论者",hidden=true)
	private String commenter;
	
	@ApiModelProperty(value="回复者")
	private String applayer;
	
	@ApiModelProperty(value="点赞量",hidden=true)
	private Long likeCount;
}
