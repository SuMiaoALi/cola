package com.baoyun.ins.entity.bi.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 帖子标签实体
 * @Author cola
 * @Date 2020年1月6日
 */

@ApiModel("帖子标签实体")
@Data
public class NoteTagVo {
	
	@ApiModelProperty(value = "id", hidden = true)
	private Long id;
	
	@ApiModelProperty("标签名")
	private String tag;
	
	@ApiModelProperty("标签ID")
	private Integer tagId;
	
	@ApiModelProperty(value = "父标签ID", hidden = true)
	private Integer parentId;
	
	@ApiModelProperty(value = "帖子ID", hidden = true)
	private Integer noteId;
	
}
