package com.baoyun.ins.controller.qe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.qe.vo.QuesVo;
import com.baoyun.ins.service.qe.QuesService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 问卷接口
 * @Author cola
 * @Date 2019年12月19日
 */

@Api(tags = "问卷接口")
@RestController
@RequestMapping("/ques")
public class QuesController {
	
	@Autowired
	private QuesService quesService;
	
	@ApiOperation("保存问卷")
	@PostMapping("/save")
	public Msg<?> save(@RequestBody QuesVo stageVo) {
		return quesService.save(stageVo);
	}
	
	@ApiOperation("查询用户有没有问题")
	@GetMapping("/hasQues")
	public Msg<?> hasQuestion(BaseVo base) {
		return quesService.hasQuestion(base);
	}
	
	@ApiOperation("查询问题详情")
	@GetMapping("/detail/{id}")
	public Msg<?> datail(@PathVariable String id) {
		return quesService.datail(id);
	}

}
