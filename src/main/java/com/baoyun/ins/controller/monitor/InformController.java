package com.baoyun.ins.controller.monitor;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.monitor.dto.InformDto;
import com.baoyun.ins.entity.monitor.vo.InformVo;
import com.baoyun.ins.service.monitor.InformService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 举报接口
 * @author xiewei
 *
 */
@Api(tags="举报接口")
@RestController
@RequestMapping(value = "/inform")
public class InformController {
	
	@Autowired
	private InformService informService;
	
	@ApiOperation("查询基础数据")
	@GetMapping("/bi")
	public Msg<List<InformDto>> bi(){
		return informService.listBi();
	}
	
	@ApiOperation("举报")
	@PostMapping
	public Msg<?> save(@Valid @RequestBody InformVo informVo){
		return informService.save(informVo);
	}
}