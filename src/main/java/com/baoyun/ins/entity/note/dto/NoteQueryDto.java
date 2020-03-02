package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 帖子列表查询实体
 * @Author cola
 * @Date 2020年1月3日
 */

@ApiModel("帖子列表查询实体")
@Data
@Accessors(chain = true)
public class NoteQueryDto implements Serializable {
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
	
	@ApiModelProperty("封面")
	private String cover;
	
	@ApiModelProperty("头像")
	private String photo;
	
	@ApiModelProperty("昵称")
	private String name;
	
	@ApiModelProperty("用户ID")
	private String author;
	
	@ApiModelProperty("点赞数")
	private Long likeCount;
	
	@ApiModelProperty("收藏数")
	private Long collectionCount;
	
	@ApiModelProperty("浏览量")
	private Long viewCount;
	
	@ApiModelProperty("是否已删除")
	private String delete;
	
	@ApiModelProperty("是否点赞")
	private String isLiked;
	
	@ApiModelProperty("是否收藏")
	private String isCollected;
	
	@ApiModelProperty("状态, 0-待审核，1-已通过")
	private String status;

}
