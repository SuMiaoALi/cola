package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;
import java.util.List;

import com.baoyun.ins.utils.pagehelper.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 帖子评论实体
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("帖子评论实体")
@Data
@Accessors(chain = true)
public class NoteCommentDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Long id;
	
	@ApiModelProperty("内容")
	private String content;
	
	@ApiModelProperty("评论人ID")
	private String userId;
	
	@ApiModelProperty("头像")
	private String photo;
	
	@ApiModelProperty("昵称")
	private String name;

	@ApiModelProperty("点赞量")
	private Long likeCount;
	
	@ApiModelProperty("是否点赞")
	private String isLiked;
	
	@ApiModelProperty("评论时间")
	private String time;
	
	@ApiModelProperty("回复")
	private Page<NoteCommentApplyDto> applies;
	
	@ApiModelProperty("父评论id")
	private Long applyId;
	
	@ApiModelProperty("子集")
	private List<NoteCommentDto> children;
	
}
