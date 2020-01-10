package com.baoyun.ins.utils.json;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 公共返回消息实体
 * @author louis
 *
 */
@ApiModel
@Data
public class Msg<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 消息
     */
	@ApiModelProperty(value="状态码描述")
    private String message;
    /**
     * 返回码
     */
	@ApiModelProperty(value="状态码")
    private String code;
    /**
     * 额外的数据
     */
	@ApiModelProperty(value="数据")
    private T data;
	
    
	/**
	 * 通过code获取提示信息
	 * @param code
	 * @param data
	 */
	public Msg(String code, T data) {
		this.code = code;
        this.data = data;
        this.message = ReturnCodeUtil.getMsg(code);
    }
	
	/**
	 * 通过code获取提示信息
	 * @param code
	 */
	public Msg(String code) {
		this.code = code;
        this.message = ReturnCodeUtil.getMsg(code);
    }
	
	/**
	 * 默认
	 */
	public Msg() {
        this.code = GlobalReturnCode.SUCCESS;
    }
	
	/**
	 * 查询数据成功
	 * @param data
	 */
	public Msg(T data) {
        this.code = GlobalReturnCode.SUCCESS;
        this.data = data;
    }

}
