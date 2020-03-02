package com.baoyun.ins.service.keyword.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.keyword.dto.KeywordNoteDto;
import com.baoyun.ins.mapper.keyword.KeywordMapper;
import com.baoyun.ins.service.keyword.KeywordService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.spring.SpringContextUtil;

/**
 * @Description: 关键词接口
 * @Author cola
 * @Date 2020年2月28日
 */

@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	private KeywordMapper keywordMapper;
	
	/**
	 *关键字查询帖子列表
	 */
	@Override
	public Msg<?> listNote(String keyword) {
		// TODO Auto-generated method stub
		List<KeywordNoteDto> list = keywordMapper.listNote(keyword);
		return new Msg<>(list);
	}

	/**
	 *保存关键字
	 */
	@Override
	public Msg<?> save(String keyword) {
		// TODO Auto-generated method stub
		String userId = SpringContextUtil.getUserId();
		keywordMapper.save(userId, keyword);
		return new Msg<>();
	}

	/**
	 *删除关键字
	 */
	@Override
	public Msg<?> delete(String keyword) {
		// TODO Auto-generated method stub
		String userId = SpringContextUtil.getUserId();
		keywordMapper.delete(userId, keyword);
		return new Msg<>();
	}

}
