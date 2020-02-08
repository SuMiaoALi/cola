package com.baoyun.ins.service.tag.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.bi.dto.TagDto;
import com.baoyun.ins.mapper.tag.TagMapper;
import com.baoyun.ins.service.tag.TagService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.redis.RedisConstant;
import com.baoyun.ins.utils.redis.RedisUtil;

/**
 * @Description: 标签接口
 * @Author cola
 * @Date 2020年1月12日
 */

@Service
public class TagServiceImpl implements TagService {
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private TagMapper tagMapper;

	/**
	 *查询标签列表
	 */
	@Override
	public Msg<?> list() {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<>();
//		if (redisUtil.exists(RedisConstant.BI_TAGS)) {
//			msg.setData(redisUtil.get("RedisConstant.BI_TAGS", List.class));
//			return msg;
//		}
		List<TagDto> list = tagMapper.list();
//		redisUtil.set(RedisConstant.BI_TAGS, _list);
		msg.setData(list);
		return msg;
	}
	
}
