package com.baoyun.ins.entity.note.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 帖子审核实体
 * @Author cola
 * @Date 2020年1月12日
 */
@ApiModel("帖子审核入参")
@Data
@Accessors(chain = true)
public class NoteApproveVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("帖子id 通过不传原因，拒绝全部都要传")
	private Long noteId;
	
	@ApiModelProperty("审核人id")
	private String approver;
	
	@ApiModelProperty("拒绝原因")
	private String reason;
	
	@ApiModelProperty("帖子审核状态")
	private Integer status;
	
	@ApiModelProperty("标签")
	private String tags;

}
