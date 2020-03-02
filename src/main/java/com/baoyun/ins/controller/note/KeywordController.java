package com.baoyun.ins.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.service.keyword.KeywordService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 搜索关键词
 * @Author cola
 * @Date 2020年2月28日
 */

@Api(tags = "关键词接口")
@RestController
@RequestMapping("/keyword")
public class KeywordController {
	
	@Autowired
	private KeywordService keywordSerice;
	
	@ApiOperation("根据关键词查询帖子")
	@GetMapping("/{keyword}")
	public Msg<?> listNote(@PathVariable String keyword) {
		System.out.println(keyword);
		return keywordSerice.listNote(keyword);
	}	
	
	@ApiOperation("保存用户关键字")
	@PostMapping("/save/{keyword}")
	public Msg<?> save(@PathVariable String keyword) {
		return keywordSerice.save(keyword);
	}	
	
	@ApiOperation("删除用户关键字")
	@DeleteMapping("/del/{keyword}")
	public Msg<?> delete(@PathVariable String keyword) {
		return keywordSerice.delete(keyword);
	}	
	
}
