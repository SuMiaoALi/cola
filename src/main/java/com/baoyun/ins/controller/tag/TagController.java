package com.baoyun.ins.controller.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.service.tag.TagService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 标签接口
 * @Author cola
 * @Date 2020年1月12日
 */

@Api(tags = "标签接口")
@RestController
@RequestMapping("/tag")
public class TagController {
	
	@Autowired
	private TagService tagService;
	
	@ApiOperation("查询系统标签")
	@GetMapping
	public Msg<?> list() {
		return tagService.list();
	}

}
