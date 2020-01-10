package com.baoyun.ins.entity.note.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 后台帖子列表实体
 * @Author cola
 * @Date 2020年1月6日
 */
@ApiModel("后台帖子列表实体")
@Data
@Accessors(chain = true)
public class ManagerNoteQueryDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Integer id; //修改了String
	
	@ApiModelProperty("标题")
	private String title;
	 
	@ApiModelProperty("昵称")
	private String authorName;
	
	@ApiModelProperty("用户ID")
	private String author;
	
	@ApiModelProperty("作者类型")
	private Integer authorType;
	
	@ApiModelProperty("浏览量")
	private Long viewCount;
	
	@ApiModelProperty("评论数")
	private Long commentCount;
	
	@ApiModelProperty("点赞数")
	private Long likeCount;
	
	@ApiModelProperty("收藏数")
	private Long collectionCount;
	 
	@ApiModelProperty("状态：0-待审核，1-审核通过，2-审核不通过")
	private Integer status ;
	
	@ApiModelProperty("0-未删除，1-已删除")
	private Integer isDelete;
	
	@ApiModelProperty("0-草稿，1-不是草稿")
	private Integer isDraft;
	
	@ApiModelProperty("生成时间")
	private String publishTime;
	
	@ApiModelProperty("内容")
	private String content;
	
	@ApiModelProperty("封面")
	private String cover;
	
	@ApiModelProperty("是否是热帖：0不是1是")
	private String isHot;
	
	@ApiModelProperty("标签名")
	private String tagName;
	
	@ApiModelProperty("标签id")
	private Integer tagId;
	
}
