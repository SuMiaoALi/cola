package com.baoyun.ins.controller.note.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.entity.note.vo.ManagerNoteQueryVo;
import com.baoyun.ins.service.note.manager.ManagerNoteService;
import com.baoyun.ins.utils.json.Msg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 后台帖子管理接口
 * @Author cola
 * @Date 2020年1月6日
 */

@Api(tags = "后台帖子接口")
@RequestMapping("/manager/note")
@RestController
public class ManagerNoteController {
	
	@Autowired
	private ManagerNoteService noteService;
	
	@ApiOperation("后台笔记列表")
	@GetMapping("/list")
	public Msg<?> list(ManagerNoteQueryVo noteQueryVo){
		return noteService.list(noteQueryVo);
	}
	
	@ApiOperation("彻底删除笔记")
	@DeleteMapping("/{id}")
	public Msg<?> delete(@PathVariable long id){
		return noteService.delete(id);
	}
	
	@ApiOperation("查询用户的笔记详情")
	@GetMapping("/{id}")
	public Msg<?> detail(@PathVariable long id) {
		return noteService.get(id);
	} 
	
	
}
