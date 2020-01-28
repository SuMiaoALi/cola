package com.baoyun.ins.controller.note;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoyun.ins.aspect.Notify;
import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.vo.CommentVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;
import com.baoyun.ins.service.note.CommentService;
import com.baoyun.ins.service.note.NoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 评论接口
 * @author xiewei
 *
 */
@Api(tags="评论接口")
@RestController
@RequestMapping(value = "/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private NoteService noteService;
	
	@ApiOperation("查询评论")
	@GetMapping
	public Msg<Page<NoteCommentDto>> list(NoteCommentQueryVo noteCommentQueryVo){
		return commentService.list(noteCommentQueryVo);
	}
	
	@ApiOperation("查询评论")
	@GetMapping("/applies")
	public Msg<Page<NoteCommentApplyDto>> applies(NoteCommentQueryVo noteCommentQueryVo){
		return commentService.applies(noteCommentQueryVo);
	}
	
	@ApiOperation("保存评论")
	@PostMapping
	@Notify(type = "C", method = "A")
	public Msg<?> save(@Valid @RequestBody CommentVo commentVo){
		return commentService.save(commentVo);
	}
	
	@ApiOperation("点赞")
	@PutMapping("/like/{id}")
	public Msg<?> like(@PathVariable long id){
		return noteService.operate(id, 7);
	}
	
	@ApiOperation("取消点赞")
	@PutMapping("/unlike/{id}")
	public Msg<?> unlike(@PathVariable long id){
		return noteService.operate(id, 8);
	}
}