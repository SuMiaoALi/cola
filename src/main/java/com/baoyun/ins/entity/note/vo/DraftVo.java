package com.baoyun.ins.entity.note.vo;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baoyun.ins.entity.bi.vo.NoteTagVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 草稿箱
 * @author xiewei
 *
 */
@Data
public class DraftVo {
	
	@ApiModelProperty(value="id",hidden=true)
	private int id;
	
	@ApiModelProperty(value="标题") 
	private String title;
	
	@ApiModelProperty(value="内容")
	private String content;
	
	@ApiModelProperty(value="封面")
	private String cover;
	
	@ApiModelProperty(value="草稿")
	private String draft;
	
	@ApiModelProperty(value="描述",hidden=true)
	private String description;
	
	@ApiModelProperty(value="标签")
	private List<NoteTagVo> tags;
	
	@ApiModelProperty(value="作者",hidden=true)
	private String author;
	
	@ApiModelProperty(value="类型",hidden=true)
	private String type;
	
	@ApiModelProperty(value="图片")
	private List<NoteImgVo> images;

}
