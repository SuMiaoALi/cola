package com.baoyun.ins.entity.note.vo;

import com.baoyun.ins.entity.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 后台帖子列表查询实体
 * @Author cola
 * @Date 2020年1月6日
 */
@ApiModel("后台帖子列表查询实体")
@Data
@Accessors(chain = true)
public class ManagerNoteQueryVo extends BaseVo {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("文章名")
	private String noteName;
 
	@ApiModelProperty("用户ID")
	private String userId;
	
	@ApiModelProperty("作者")
	private String author;
	
	@ApiModelProperty("作者类型")
	private Integer authorType;
	
	@ApiModelProperty("状态：-2-已删除，-1-草稿，0-待审核，1-已通过，2-未通过")
	private String status;
	
	@ApiModelProperty("是否热帖:0不是 1是")
	private String isHot;
	
	@ApiModelProperty("排序规则")
	private String order;
	
	@ApiModelProperty("排序字段")
	private String property;
	
	@ApiModelProperty("编号")
	private Integer id;
	
	@ApiModelProperty("标签id")
	private Integer tagId;

}
