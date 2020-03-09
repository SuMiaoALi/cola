package com.baoyun.ins.entity.note.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 帖子屏蔽实体
 * @Author cola
 * @Date 2020年3月6日
 */

@ApiModel("帖子屏蔽实体")
@Data
public class ShieldNoteVo implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("帖子id")
	private Long noteId;
	
	@ApiModelProperty("用户id")
	private Long userId;

}
