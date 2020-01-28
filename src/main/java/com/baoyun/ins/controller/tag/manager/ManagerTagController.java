package com.baoyun.ins.controller.tag.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.tag.vo.TagVo;
import com.baoyun.ins.service.tag.manager.ManagerTagService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 后台标签接口
 * @Author cola
 * @Date 2020年1月12日
 */

@Api(tags = "后台标签接口")
@RestController
@RequestMapping("/tag")
public class ManagerTagController {

	@Autowired
	private ManagerTagService tagService;
	
	@ApiOperation("获取标签列表")
	@GetMapping("/baseTags")
	public Msg<?> list(BaseVo base) {
		return tagService.list(base);
	}
	
	@ApiOperation("添加标签")
	@PostMapping()
	public Msg<?> insert(@RequestBody TagVo tagVo) {
		return tagService.insert(tagVo);
	}
	
	@ApiOperation("删除标签")
	@DeleteMapping("/{id}")
	public Msg<?> delete(@PathVariable Integer id) {
		return tagService.delete(id);
	}
	
	@ApiOperation("修改标签")
	@PutMapping()
	public Msg<?> update(@RequestBody TagVo tagVo){
		return tagService.update(tagVo);
	}

}
