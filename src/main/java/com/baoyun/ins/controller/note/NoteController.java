package com.baoyun.ins.controller.note;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.aspect.Notify;
import com.baoyun.ins.config.annotation.CanTourist;
import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.CommentQueryVo;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.service.note.NoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

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
		System.out.println(noteQueryVo);
		return noteService.list(noteQueryVo);
	}
	
	@ApiOperation("查询笔记详情")
	@GetMapping("/{id}")
	@CanTourist(true)
	public Msg<?> detail(@PathVariable Long id){
		return noteService.get(id);
	}
	
	@ApiOperation("查询用户的笔记")
	@GetMapping("/userId/{id}")
	@CanTourist(value = true)
	public Msg<?> others(@PathVariable String id, BaseVo baseVo){
		System.out.println("接收的查阅寻用户的id：" + id);
		// 是当前登录用户，即查询自己的发布
		if ("self".equals(id)) {
			return noteService.mine(baseVo);
		}
		// 否则是查询别人的
		NoteQueryVo noteQueryVo = new NoteQueryVo();
		noteQueryVo.setAuthor(id);
		noteQueryVo.setStart(baseVo.getStart());
		noteQueryVo.setPageSize(baseVo.getPageSize());
		return noteService.list(noteQueryVo);
	}
	
	@ApiOperation("查询收藏帖子")
	@GetMapping("/mine/collect/{userId}")
	public Msg<?> myCollection(@PathVariable String userId, BaseVo baseVo){
		return noteService.myCollection(userId, baseVo);
	}
	
	@ApiOperation("查询点赞帖子")
	@GetMapping("/mine/like/{userId}")
	public Msg<?> myLike(@PathVariable String userId, BaseVo baseVo){
		return noteService.myLike(userId, baseVo);
	}
	
	@ApiOperation("查询屏蔽帖子")
	@GetMapping("/mine/shield/{userId}")
	public Msg<?> myShield(@PathVariable String userId, BaseVo baseVo){
		return noteService.myShield(userId, baseVo);
	}
	
	@ApiOperation("保存笔记")
	@PostMapping("/save")
	public Msg<?> save(List<MultipartFile> file, NoteVo noteVo){
		if (file == null || !(file.size() > 0)) {
			Msg<Object> msg = new Msg<>();
			msg.setCode("30002");
			msg.setData("图片为空");
			return msg;
		}
		return noteService.save(file, noteVo);
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
	
	@ApiOperation("查询帖子评论")
	@GetMapping("/comment")
	public Msg<?> comment(CommentQueryVo commentVo) {
		return noteService.comment(commentVo);
	}
	
	@ApiOperation("查询评论下的回复")
	@GetMapping("/reply/{commentId}")
	public Msg<?> reply (@PathVariable Long commentId, BaseVo baseVo) {
		return noteService.reply(commentId, baseVo);
	}
	
	@ApiOperation("屏蔽帖子")
	@PostMapping("/shield/{noteId}")
	public Msg<?> shield(@PathVariable Long noteId) {
		return noteService.shield(noteId);
	}
	
	@ApiOperation("取消屏蔽帖子")
	@PostMapping("/unshield/{noteId}")
	public Msg<?> unshield(@PathVariable Long noteId) {
		return noteService.unshield(noteId);
	}
	
}