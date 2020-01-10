package com.baoyun.ins.entity.note.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NoteImgVo {
	
	@ApiModelProperty(value="id",hidden=true)
	private int id;
	
	@ApiModelProperty(value="图片地址")
	private String url;
	
	@ApiModelProperty(value="图片类型:0用户 1图库")
	private String type;
	
	@ApiModelProperty(value="帖子ID",hidden=true)
	private int noteId;
	
}
