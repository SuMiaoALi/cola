package com.baoyun.ins.entity.note.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.baoyun.ins.entity.bi.vo.NoteTagVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 发布帖子实体
 * @Author cola
 * @Date 2020年1月3日
 */

@ApiModel("发布帖子实体")
@Data
@Accessors(chain = true)
public class NoteVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "id")
	private Integer id;
	
	@ApiModelProperty("标题")
	@NotBlank(message = "标题必填")
	@Length(max = 140, message = "标题不能超过140字")
	private String title;
	
	@ApiModelProperty("内容")
	private String content;
	
	@ApiModelProperty(value = "封面", hidden = true)
	private String cover;
	
	@ApiModelProperty(value = "描述", hidden = true)
	private String description;
	
	@ApiModelProperty("标签")
	private List<NoteTagVo> tags;
	
	@ApiModelProperty(value = "作者", hidden = true)
	private String author;
	
	@ApiModelProperty(value = "是否是草稿, 1-不是", hidden = true)
	private String isDraft;
	
	@ApiModelProperty("状态： 0-待审核，1-通过，2-拒绝")
	private String status;
	
	@ApiModelProperty("图片")
	private List<NoteImgVo> images;
	
}
