package com.baoyun.ins.entity.monitor.vo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class InformVo {
	
	@ApiModelProperty(value="id",hidden=true)
	private int id;
	
	@ApiModelProperty(value="违规项")
	@Min(value=1,message="违规项必填")
	private int informId;
	
	@ApiModelProperty(value="内容")
	@Length(max=200,message="内容不能超过200字")
	private String content;
	
	@ApiModelProperty(value="类型")
	private String type;
	
	@ApiModelProperty(value="举报对象ID")
	@NotBlank(message="举报对象ID必填")
	private String objectId;
	
	@ApiModelProperty(value="图片,以逗号分割，图片名称以时间戳命名")
	private String images;
	
	@ApiModelProperty(value="用户ID",hidden=true)
	private String userId;
}
