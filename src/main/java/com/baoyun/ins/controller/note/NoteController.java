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

import com.baoyun.ins.aspect.Notify;
import com.baoyun.ins.config.annotation.CanTourist;
import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.service.note.NoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 帖子接口
 * @Author cola
 * @Date 2020年1月3日
 */
@Api(tags = "帖子接口")
@RestController
@RequestMapping("/note")
public class NoteController {
	
	@Autowired
	private NoteService noteService;
	
	@ApiOperation("查询笔记列表")
	@GetMapping
	@CanTourist(true)
	public Msg<Page<NoteQueryDto>> list(NoteQueryVo noteQueryVo){
		noteQueryVo.setStatus("1");
		return noteService.list(noteQueryVo);
	}
	
	@ApiOperation("查询笔记详情")
	@GetMapping("/{id}")
	@CanTourist(true)
	public Msg<?> detail(@PathVariable Long id){
		return noteService.get(id);
	}
	
	@ApiOperation("查询用户的笔记")
	@GetMapping("/userid/{id}")
	@CanTourist(value = true)
	public Msg<Page<NoteQueryDto>> others(@PathVariable String id, BaseVo baseVo){
		NoteQueryVo noteQueryVo = new NoteQueryVo();
		noteQueryVo.setAuthor(id);
		noteQueryVo.setStatus("1");
		noteQueryVo.setStart(baseVo.getStart());
		noteQueryVo.setPageSize(baseVo.getPageSize());
		return noteService.list(noteQueryVo);
	}
	
	@ApiOperation("查询我的笔记")
	@GetMapping("/mine")
	public Msg<Page<NoteQueryDto>> mine(BaseVo baseVo){
		NoteQueryVo noteQueryVo = new NoteQueryVo();
		noteQueryVo.setAuthor(SpringContextUtil.getUserId());
		noteQueryVo.setStart(baseVo.getStart());
		noteQueryVo.setStatus(null);
		noteQueryVo.setPageSize(baseVo.getPageSize());
		return noteService.list(noteQueryVo);
	}
	
	@Notify(type = "note", method = "A")
	@ApiOperation("保存笔记")
	@PostMapping
	public Msg<?> save(@Valid @RequestBody NoteVo noteVo){
		return noteService.save(noteVo);
	}
	
	@ApiOperation("点赞")
	@PutMapping("/like/{id}")
	public Msg<?> like(@PathVariable Long id){
		return noteService.operate(id, 0);
	}
	
	@ApiOperation("取消点赞")
	@PutMapping("/unlike/{id}")
	public Msg<?> unlike(@PathVariable Long id){
		return noteService.operate(id, 1);
	}
	
	@Notify(type = "collection", method = "A")
	@ApiOperation("收藏")
	@PutMapping("/collect/{id}")
	public Msg<?> collect(@PathVariable Long id){
		return noteService.operate(id, 2);
	}
	@Notify(type = "collection", method = "D")
	@ApiOperation("取消收藏")
	@PutMapping("/uncollect/{id}")
	public Msg<?> uncollect(@PathVariable Long id){
		return noteService.operate(id, 3);
	}
	
	@ApiOperation("分享")
	@PutMapping("/share/{id}")
	public Msg<?> share(@PathVariable Long id){
		return noteService.operate(id, 4);
	}
	
	@ApiOperation("删除")
	@DeleteMapping("/{id}")
	public Msg<?> delete(@PathVariable Long id){
		return noteService.delete(id);
	}
}