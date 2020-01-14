package com.baoyun.ins.controller.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.news.vo.GetTokenVo;
import com.baoyun.ins.entity.news.vo.MiniTemplateVo;
import com.baoyun.ins.service.news.NewsService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 消息管理
 * @Author cola
 * @Date 2020年1月12日
 */

@Api(tags = "消息接口")
@RequestMapping("/news")
@RestController
public class NewsController {

	@Autowired
	private NewsService newsService;
	
	@ApiOperation("小程序发送订阅消息")
	@PostMapping("/mini")
	public Msg<?> sendMini(@RequestBody MiniTemplateVo data, GetTokenVo tokenVo) {
		return newsService.sendMini(data, tokenVo);
	}
	
}
