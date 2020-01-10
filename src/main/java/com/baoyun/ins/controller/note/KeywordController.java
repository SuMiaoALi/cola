package com.baoyun.ins.controller.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.config.annotation.CanTourist;
import com.baoyun.ins.entity.note.dto.KeywordDto;
import com.baoyun.ins.entity.note.vo.KeywordQueryVo;
import com.baoyun.ins.service.note.KeywordService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @Description: 关键字接口
 * @Author cola
 * @Date 2020年1月6日
 */

@Api(tags = "关键字接口")
@RestController
@RequestMapping("/keyword")
public class KeywordController {

	@Autowired
	private KeywordService keywordService;
	
	@ApiOperation("用户关键字查询")
	@GetMapping
	@CanTourist(value = true)
	public Msg<Page<KeywordDto>> list(KeywordQueryVo KeywordVo){
		return keywordService.list(KeywordVo);
	}
	
	@ApiOperation("关键字删除")
	@DeleteMapping("/{keyword}")
	public Msg<?> delete(@PathVariable String keyword){
		return keywordService.delete(keyword);
	}
	
	@ApiOperation("匹配关键字查询")
	@GetMapping("/allKeyword")
	@CanTourist(value = true)
	public Msg<Page<KeywordDto>> allKeyword(KeywordQueryVo KeywordVo){
		return keywordService.allKeyword(KeywordVo);
	}
}
