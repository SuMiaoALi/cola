package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;
import java.util.List;

import com.baoyun.ins.utils.pagehelper.Page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 帖子详情实体
 * @Author cola
 * @Date 2020年1月6日
*/

@ApiModel("帖子详情实体")
@Data
@Accessors(chain = true)
public class NoteDetailDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Integer id;
	
	@ApiModelProperty("标题")
	private String title;
	
	@ApiModelProperty("内容")
	private String content;
	
//	@ApiModelProperty("封面")
//	private String cover;
	
	@ApiModelProperty("头像")
	private String photo;
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty("作者")
	private String author;
	
	@ApiModelProperty("签名")
	private String description;
	
	@ApiModelProperty("评论量")
	private Long commentCount;
	
	@ApiModelProperty("点赞量")
	private Long likeCount;
	
	@ApiModelProperty("收藏量")
	private Long collectionCount;
	
	@ApiModelProperty("阅读量")
	private Long viewCount;
	
	@ApiModelProperty("标签")
	private String tags;
	
	@ApiModelProperty("发布时间")
	private String time;
	
	@ApiModelProperty("是否已点赞：>0")
	private String isFollow;
	
	@ApiModelProperty("是否已点赞：>0")
	private String isLiked;
	
	@ApiModelProperty("是否已转发：>0")
	private String isShared;
	
	@ApiModelProperty("是否收藏：>0")
	private String isCollected;
	
	@ApiModelProperty("是否收藏：0 否，1是")
	private String isMine;
	
//	@ApiModelProperty("评论")
//	private Page<NoteCommentDto> comments;
//	
//	@ApiModelProperty("评论列表")
//	private List<NoteCommentDto> commentList;
	
	@ApiModelProperty("推荐")
	private List<NoteQueryDto> recommend;
	
	@ApiModelProperty("帖子图片")
	private List<String> imgs;
	
}
