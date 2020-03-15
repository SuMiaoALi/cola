package com.baoyun.ins.service.note.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.dto.NoteOperateDto;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.CommentQueryVo;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.mapper.note.CommentMapper;
import com.baoyun.ins.mapper.note.NoteMapper;
import com.baoyun.ins.service.note.NoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.uoload.FileUploadUtil;
import com.github.pagehelper.PageHelper;

/**
 * @Description: 帖子接口
 * @Author cola
 * @Date 2020年1月6日
 */
@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteMapper noteMapper;

	@Autowired
	private CommentMapper commentMapper;

	/**
	 * 发布帖子
	 */
	@Override
	@Transactional
	public Msg<?> save(List<MultipartFile> file, NoteVo noteVo) {
		// TODO Auto-generated method stub
		String userId = SpringContextUtil.getUserId();
		// 为了测试演示方便，默认为通过
		noteVo.setAuthor(userId).setStatus("1").setDelete("0");
		noteMapper.save(noteVo);
		noteMapper.saveContent(noteVo);
		// 添加图片
		for (int i = 0; i < file.size(); i++) {
			// 先存第一张图片,设置封面
			if (i == 0) {
				String firName = FileUploadUtil.uploadImage(file.get(i));
				noteMapper.updateCover(noteVo.getId(), firName);
				noteMapper.addImg(noteVo.getId(), firName);
				continue;
			}
			noteMapper.addImg(noteVo.getId(), FileUploadUtil.uploadImage(file.get(i)));
		}
		// 添加标签
		noteMapper.addTag(noteVo.getId(),noteVo.getTagId());
		return new Msg<>(noteVo.getId());

	}

	/**
	 * 查询笔记列表
	 */
	@Override
	public Msg<Page<NoteQueryDto>> list(NoteQueryVo noteQueryVo) {
		noteQueryVo.setUserId(SpringContextUtil.getUserId());
		PageHelper.startPage(noteQueryVo.getStart(), noteQueryVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.list(noteQueryVo);
		Page<NoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 点赞等操作
	 */
	@Override
	public Msg<?> operate(Long id, Integer type) {
		// TODO Auto-generated method stub
		Msg<?> msg = new Msg<>();
		String userId = SpringContextUtil.getUserId();
		NoteOperateDto nod = noteMapper.operate(userId == null ? "" : userId, id, type);
		msg.setCode(nod.getRs());
		return msg;
	}

	/**
	 * 帖子详情
	 */
	@Override
	public Msg<NoteDetailDto> get(Long id) {
		// TODO Auto-generated method stub
		String userId = SpringContextUtil.getUserId();
		NoteDetailDto ndd = noteMapper.get(id, userId);
		if (ndd != null) {
			// 图片，标签
			ndd.setImgs(noteMapper.getImg(id));
			ndd.setTags(noteMapper.getTags(id));
			// 推荐帖
			ndd.setRecommend(noteMapper.recommend(id, userId));
			ndd.setViewCount(ndd.getViewCount() + 1);
		}
		this.operate(id, 5);
		return new Msg<>(ndd);
	}

	/**
	 * 删除当前用户的帖子
	 */
	@Override
	public Msg<?> delete(Long id) {
		// TODO Auto-generated method stub
		noteMapper.delete(SpringContextUtil.getUserId(), id);
		return new Msg<>();
	}

	/**
	 * 查询帖子评论
	 */
	@Override
	public Msg<?> comment(CommentQueryVo commentVo) {
		// TODO Auto-generated method stub
		// 帖子id
		Long noteId = commentVo.getId();
		// 只查询评论
		PageHelper.startPage(commentVo.getStart(), commentVo.getPageSize());
		List<NoteCommentDto> parentList = commentMapper.list1(SpringContextUtil.getUserId(), noteId);
		// 只查询回复
		List<NoteCommentDto> childrenList = commentMapper.list2(SpringContextUtil.getUserId(), noteId);

		for (NoteCommentDto parent : parentList) {
			List<NoteCommentDto> list = new ArrayList<>();
			for (NoteCommentDto children : childrenList) {
				if (children.getApplyId() == parent.getId()) {
					children.setChildren(new ArrayList<>());
					list.add(children);
					// 默认丢4条
					if (list.size() > 3)
						break;
				}
			}
			parent.setChildren(list);
		}
		Page<NoteCommentDto> pageList = new Page<>(parentList);
		return new Msg<>(pageList);
	}
	
	/**
	 * 查询评论下的回复
	 */
	@Override
	public Msg<?> reply(Long commentId, BaseVo baseVo) {
		// TODO Auto-generated method stub
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());
		List<NoteCommentDto> list = commentMapper.list3(SpringContextUtil.getUserId(), commentId);
		Page<NoteCommentDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 我收藏的
	 */
	@Override
	public Msg<?> myCollection(String userId, BaseVo baseVo) {
		// TODO Auto-generated method stub
		// 当前用户
		if ("self".equals(userId)) {
			userId = SpringContextUtil.getUserId();
		}
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.myCollection(userId);
		Page<NoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 我发布的
	 */
	@Override
	public Msg<?> mine(BaseVo baseVo) {
		// TODO Auto-generated method stub
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.mine(SpringContextUtil.getUserId());
		Page<NoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 我点攒的
	 */
	@Override
	public Msg<?> myLike(String userId, BaseVo baseVo) {
		// TODO Auto-generated method stub
		// 当前用户
		if ("self".equals(userId)) {
			userId = SpringContextUtil.getUserId();
		}
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.myLike(userId);
		Page<NoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 我屏蔽的
	 */
	@Override
	public Msg<?> myShield(String userId, BaseVo baseVo) {
		// TODO Auto-generated method stub
		// 当前用户
		if ("self".equals(userId)) {
			userId = SpringContextUtil.getUserId();
		}
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.myShield(userId);
		Page<NoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<>(pageInfo);
	}

	/**
	 * 屏蔽帖子
	 */
	@Override
	public Msg<?> shield(Long noteId) {
		// TODO Auto-generated method stub
		noteMapper.shield(noteId, SpringContextUtil.getUserId());
		return new Msg<>();
	}

	/**
	 * 取消屏蔽
	 */
	@Override
	public Msg<?> unshield(Long noteId) {
		// TODO Auto-generated method stub
		noteMapper.unshield(SpringContextUtil.getUserId(), noteId);
		return new Msg<>();
	}

}
