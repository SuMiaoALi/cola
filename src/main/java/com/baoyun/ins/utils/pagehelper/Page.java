package com.baoyun.ins.utils.pagehelper;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.PageSerializable;

public class Page<T> extends PageSerializable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean hasMore;
	
	public Page() {}

	/**
	 * 包装Page对象
	 * @param list
	*/
	public Page(List<T> list) {
		super(list);
	}
	
	/**
     * 包装Page对象
     *
     * @param list
     */
    public Page(List<T> list,int pageNum,int pageSize) {
    	long _total = 0;
    	if(list instanceof com.github.pagehelper.Page){
    		com.github.pagehelper.Page<T> page = (com.github.pagehelper.Page<T>)list;
    		_total = page.getTotal();
            if(page.getPages() > pageNum) {
            	this.hasMore = true;
            }
        } else {
        	_total = list.size();
        }
    	if((pageNum -1)*pageSize < _total) {
    		this.setList(list);
    	}else {
    		this.setList(new ArrayList<T>());
    	}
    	this.setTotal(_total);
    }
}
