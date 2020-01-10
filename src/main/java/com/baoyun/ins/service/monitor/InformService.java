package com.baoyun.ins.service.monitor;

import java.util.List;

import com.baoyun.ins.entity.monitor.dto.InformDto;
import com.baoyun.ins.entity.monitor.vo.InformVo;
import com.baoyun.ins.utils.json.Msg;

/**
 * 举报业务逻辑处理接口类
 * @author xiewei
 *
 */
public interface InformService {
	
	/**
	 * 举报
	 * @param informVo
	 * @return
	 */
	Msg<?> save(InformVo informVo);
	
	/**
	 * 查询举报基础数据
	 * @return
	 */
	Msg<List<InformDto>> listBi();

}
