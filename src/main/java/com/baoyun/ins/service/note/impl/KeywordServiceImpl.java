package com.baoyun.ins.service.note.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.note.dto.KeywordDto;
import com.baoyun.ins.entity.note.vo.KeywordQueryVo;
import com.baoyun.ins.mapper.note.KeywordMapper;
import com.baoyun.ins.service.note.KeywordService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.github.pagehelper.PageHelper;
@Service
public class KeywordServiceImpl implements KeywordService {

	@Autowired
	private KeywordMapper keywordMapper;
	
	/**
	 *关键字列表
	 */
	@Override
	public Msg<Page<KeywordDto>> list(KeywordQueryVo keywordVo) {
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		keywordVo.setCurrentUserId(userId);
		PageHelper.startPage(keywordVo.getStart(), keywordVo.getPageSize());	
		List<KeywordDto> list = keywordMapper.list(keywordVo.getCurrentUserId());
		Page<KeywordDto> pageInfo = new Page<KeywordDto>(list,keywordVo.getStart(), keywordVo.getPageSize());
		return new Msg<Page<KeywordDto>>(pageInfo); 
	}
	
	/**
	 *删除关键字
	 */
	@Override
	public Msg<?> delete(String keyword) {
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		keywordMapper.delete(userId, keyword);
		return new Msg<>();
	}
	
	/**
	 *关键字匹配
	 */
	@Override
	public Msg<Page<KeywordDto>> allKeyword(KeywordQueryVo keywordVo) {
		PageHelper.startPage(keywordVo.getStart(), keywordVo.getPageSize());	
		List<KeywordDto> list = keywordMapper.allKeyword(keywordVo); 
		Page<KeywordDto> pageInfo = new Page<KeywordDto>(list, keywordVo.getStart(), keywordVo.getPageSize());
		return new Msg<Page<KeywordDto>>(pageInfo);
	}

	
}
