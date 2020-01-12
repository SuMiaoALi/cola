package com.baoyun.ins.service.tag.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.bi.dto.TagDto;
import com.baoyun.ins.entity.tag.vo.TagVo;
import com.baoyun.ins.mapper.tag.manager.ManagerTagMapper;
import com.baoyun.ins.service.tag.manager.ManagerTagService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * @Description: 标签接口
 * @Author cola
 * @Date 2020年1月12日
 */

@Service
public class ManagerTagServiceImpl implements ManagerTagService {
	
	@Autowired
	private ManagerTagMapper tagMapper;

	/**
	 *查询标签列表
	 */
	@Override
	public Msg<?> list(BaseVo base) {
		// TODO Auto-generated method stub
		PageHelper.startPage(base.getStart(), base.getPageSize());
		List<TagDto> list = tagMapper.list();
		Page<TagDto> page = new Page<>(list);
		return new Msg<>(page);
	}

	/**
	 *增加标签
	 */
	@Override
	public Msg<?> insert(TagVo tagVo) {
		// TODO Auto-generated method stub
		Integer id = tagMapper.insert(tagVo);
		return new Msg<>(id);
	}

	/**
	 *删除标签
	 */
	@Override
	public Msg<?> delete(Integer id) {
		// TODO Auto-generated method stub
		tagMapper.delete(id);
		return new Msg<>();
	}

	/**
	 *修改标签
	 */
	@Override
	public Msg<?> update(TagVo tagVo) {
		// TODO Auto-generated method stub
		tagMapper.update(tagVo);
		return new Msg<>();
	}

}
