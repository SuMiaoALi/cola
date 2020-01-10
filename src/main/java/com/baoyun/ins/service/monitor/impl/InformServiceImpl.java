package com.baoyun.ins.service.monitor.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.monitor.dto.InformDto;
import com.baoyun.ins.entity.monitor.vo.InformVo;
import com.baoyun.ins.mapper.monitor.InformMapper;
import com.baoyun.ins.service.monitor.InformService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.spring.SpringContextUtil;

@Service
public class InformServiceImpl implements InformService {
	
	@Autowired
	private InformMapper informMapper;

	@Override
	public Msg<?> save(InformVo informVo) {
		// TODO Auto-generated method stub
		informVo.setUserId(SpringContextUtil.getUserId());
		informMapper.save(informVo);
		return new Msg<>();
	}

	// 查询举报基础信息
	@Override
	public Msg<List<InformDto>> listBi() {
		// TODO Auto-generated method stub
		List<InformDto> informs = new ArrayList<InformDto>();
		
		List<InformDto> list = informMapper.listBi();
		for(InformDto inform : list) {
			if(inform.getParentId() == 0) {
				List<InformDto> children = new ArrayList<InformDto>();
				for(InformDto _inform : list) {
					if(_inform.getParentId() > 0 && _inform.getParentId() == inform.getId()) {
						children.add(_inform);
					}
				}
				inform.setChildren(children);
				informs.add(inform);
			}
		}
		return new Msg<>(informs);
	}
	
}
