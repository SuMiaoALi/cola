package com.baoyun.ins.utils.json;

import java.io.Serializable;

/**
 * 查询数据模板
 * @author louis
 *
 */
public class QueryResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long totalRecord;//总记录数
	private Object list;//记录
	
	public QueryResult() {
	}
	
	public QueryResult(Object list,long totalRecord) {
		this.list = list;
		this.totalRecord = totalRecord;
	}
	
	public long getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
	}
	public Object getList() {
		return list;
	}
	public void setList(Object list) {
		this.list = list;
	}
}
