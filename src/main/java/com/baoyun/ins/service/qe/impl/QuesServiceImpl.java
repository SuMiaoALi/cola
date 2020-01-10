package com.baoyun.ins.service.qe.impl;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.qe.dto.QuesDetailDto;
import com.baoyun.ins.entity.qe.dto.QuestionListDto;
import com.baoyun.ins.entity.qe.vo.QuesVo;
import com.baoyun.ins.mapper.qe.QuesMapper;
import com.baoyun.ins.service.qe.QuesService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.redis.RedisConstant;
import com.baoyun.ins.utils.redis.RedisUtil;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.github.pagehelper.PageHelper;

/**
 * @Description: 
 * @Author cola
 * @Date 2019年12月19日
 */

@Service
public class QuesServiceImpl implements QuesService {
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private QuesMapper quesMapper;
	
	/**
	 * 保存问卷
	 */
	@Override
	public Msg<?> save(QuesVo quesVo) {
		// TODO Auto-generated method stub
		String userId = SpringContextUtil.getUserId();
		quesVo.setUserId(userId);
		quesMapper.save(quesVo);
		return new Msg<>(quesVo.getId());
	}

	/**
	 * 查询用户有没有问题，有-跳转至列表，没有返回数
	 */
	@Override
	public Msg<?> hasQuestion(BaseVo base) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<>();
		String userId = SpringContextUtil.getUserId();
		// 查询有无问题
		Integer i = quesMapper.hasQuestion(userId);
		if (i != null && i == 1) {
			PageHelper.startPage(base.getStart(), base.getPageSize());
			List<QuestionListDto> list = quesMapper.getQuestionList(userId);
			Page<QuestionListDto> pageInfo = new Page<QuestionListDto>(list);
			msg.setData(pageInfo);
		} else {
			Integer count = 0;
			if (redisUtil.exists(RedisConstant.BI_COUNT)) {
				count = redisUtil.get("RedisConstant.BI_COUNT", Integer.class);
				count += new Random().nextInt(11);
				redisUtil.set(RedisConstant.BI_COUNT, count);
			} else {
				count = 666;
				redisUtil.set(RedisConstant.BI_COUNT, count);
			}
			msg.setData(count);
		}
		return msg;
	}

	/**
	 * 问题详情
	 */
	@Override
	public Msg<?> datail(String id) {
		// TODO Auto-generated method stub
		QuesDetailDto ques = quesMapper.detail(id);
		return new Msg<>(ques);
	}

}
