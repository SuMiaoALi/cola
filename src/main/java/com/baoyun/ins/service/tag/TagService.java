package com.baoyun.ins.service.tag;

import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 标签接口
 * @Author cola
 * @Date 2020年1月12日
 */
public interface TagService {

	/**
	 * @Description: 查询标签列表
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @return
	 */
	Msg<?> list();
	
}
