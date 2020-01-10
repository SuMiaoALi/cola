package com.baoyun.ins.service.note;

import com.baoyun.ins.entity.note.dto.KeywordDto;
import com.baoyun.ins.entity.note.vo.KeywordQueryVo;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

public interface KeywordService {

	/**
	 * @Description: 用户关键字列表
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param keywordVo
	 * @return
	 */
	Msg<Page<KeywordDto>> list(KeywordQueryVo keywordVo);

	/**
	 * @Description: 删除用户关键字
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param keyword
	 * @return
	 */
	Msg<?> delete(String keyword);

	/**
	 * @Description: 匹配关键字
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param keywordVo
	 * @return
	 */
	Msg<Page<KeywordDto>> allKeyword(KeywordQueryVo keywordVo);

}
