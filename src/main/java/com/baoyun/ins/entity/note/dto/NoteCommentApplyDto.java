package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 帖子评论回复实体
 * @Author cola
 * @Date 2020年1月6日
 */

@Data
public class NoteCommentApplyDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private String id;
	
	@ApiModelProperty("内容")
	private String content;
	
	@ApiModelProperty("评论人ID")
	private String userId;
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty("头像")
	private String photo;
	
	@ApiModelProperty("评论时间")
	private String time;
	
	@ApiModelProperty("被艾特者ID")
	private String applyId;
	
	@ApiModelProperty("被艾特者")
	private String applier;
	
	@ApiModelProperty("点赞量")
	private Long likeCount;
	
	@ApiModelProperty("是否点赞")
	private String isLiked;

}
