package com.baoyun.ins.utils.json;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 公共返回消息实体
 * @author louis
 *
 */
@ApiModel
@Data
@Accessors(chain = true)
public class Msg<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * 消息
     */
	@ApiModelProperty("状态码描述")
    private String message;
    /**
     * 返回码
     */
	@ApiModelProperty("状态码")
    private String code;
    /**
     * 额外的数据
     */
	@ApiModelProperty("数据")
    private T data;
	
	/**
	 * 默认
	 */
	public Msg() {
        this.code = GlobalReturnCode.SUCCESS;
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
	 * 查询数据成功
	 * @param data
	 */
	public Msg(T data) {
        this.code = GlobalReturnCode.SUCCESS;
        this.data = data;
    }
	
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
	 * 全参构造
	 * @param code
	 * @param data
	 * @param message
	 */
	public Msg(String code, T data, String message) {
		this.code = code;
		this.data = data;
		this.message = message;
	}
	
}
