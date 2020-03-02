package com.baoyun.ins.service.keyword;

import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 
 * @Author cola
 * @Date 2020年2月28日
 */
public interface KeywordService {

	/**
	 * @Description: 关键词查询帖子列表
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keyword
	 * @return
	 */
	Msg<?> listNote(String keyword);

	/**
	 * @Description: 保存关键字
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keyword
	 * @return
	 */
	Msg<?> save(String keyword);

	/**
	 * @Description: 删除关键字
	 * @Author cola
	 * @Data: 2020年2月28日
	 * @param keyword
	 * @return
	 */
	Msg<?> delete(String keyword);

}
