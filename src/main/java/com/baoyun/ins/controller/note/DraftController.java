package com.baoyun.ins.controller.note;

import javax.validation.Valid;

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
import com.baoyun.ins.entity.note.dto.DraftQueryDto;
import com.baoyun.ins.entity.note.vo.DraftVo;
import com.baoyun.ins.service.note.DraftService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * @Description: 草稿箱接口
 * @Author cola
 * @Date 2020年1月6日
 */

@Api(tags = "草稿箱接口")
@RestController
@RequestMapping("/draft")
public class DraftController {
	
	@Autowired
	private DraftService draftService;
	
	@ApiOperation("查询草稿箱")
	@GetMapping
	public Msg<Page<DraftQueryDto>> list(BaseVo baseVo){
		return draftService.list(baseVo);
	}
	
	@ApiOperation("查询草稿箱详情")
	@GetMapping("/{id}")
	public Msg<?> detail(@PathVariable long id){
		return draftService.get(id);
	}
	
	@ApiOperation("保存草稿箱")
	@PostMapping
	public Msg<?> save(@Valid @RequestBody DraftVo draftVo){ 
		return draftService.save(draftVo);
	}
	
	@ApiOperation("修改草稿箱")
	@PutMapping
	public Msg<?> update(@Valid @RequestBody DraftVo draftVo){
		return draftService.save(draftVo);
	}
	
	@ApiOperation("删除稿箱详情")
	@DeleteMapping("/{id}")
	public Msg<?> delete(@PathVariable long id){
		return draftService.delete(id);
	}
}