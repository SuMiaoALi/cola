package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 帖子评论查询实体
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("帖子评论查询实体")
@Data
public class NoteCommentQueryVo extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Long id;
	
	@ApiModelProperty("笔记ID")
	private Long noteId;
	
	@ApiModelProperty("评论ID")
	private Long commentId;
	
	@ApiModelProperty(value = "用户ID", hidden = true)
	private String userId;
	
	@ApiModelProperty(value = "排序方式:H热度，T时间", hidden = true)
	private String order;

}
