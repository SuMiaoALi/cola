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
		Msg<Object> msg = new Msg<>();
		String userId = SpringContextUtil.getUserId();
		// 为了测试演示方便，默认为通过
		noteVo.setAuthor(userId).setStatus("1").setDelete("0");
		noteMapper.save(noteVo);
		noteMapper.saveContent(noteVo);
		msg.setData(noteVo.getId());
		// 添加图片
		if (file.size() > 0) {
			// 只有一张图片
			if (file.size() == 1) {
				String newName = FileUploadUtil.uploadImage(file.get(0));
				noteMapper.updateCover(noteVo.getId(), newName);
				noteMapper.addImg(noteVo.getId(), newName);
			}
			else {
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
			}
		}
		// 添加标签
		noteMapper.addTag(noteVo.getId(), noteVo.getTagId());
		return msg;
	}

	/**
	 * 查询笔记列表
	 */
	@Override
	public Msg<Page<NoteQueryDto>> list(NoteQueryVo noteQueryVo) {
		String userId = SpringContextUtil.getUserId();
		noteQueryVo.setUserId(userId);
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
		NoteOperateDto nod = noteMapper.operate(userId, id, type);
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
//		String userId = "addcd2394d354ebfbd8da62145d25df7";
		NoteDetailDto ndd = noteMapper.get(id, userId);
		if (ndd != null) {
			// 图片，标签
			ndd.setImgs(noteMapper.getImg(id));
			ndd.setTags(noteMapper.getTags(id));
			// 推荐帖
			ndd.setRecommend(noteMapper.recommend(id, userId));
			ndd.setViewCount(ndd.getViewCount() + 1);
		}
		// 查询日记评论
//		NoteCommentQueryVo noteCommentQueryVo = new NoteCommentQueryVo();
//		noteCommentQueryVo.setUserId(userId);
//		noteCommentQueryVo.setNoteId(id);
//		PageHelper.startPage(1, 4);	
//		// 日记评论列表
//		List<NoteCommentDto> list = commentMapper.list(noteCommentQueryVo);
//		
//		for(NoteCommentDto ncd : list) {
//			if(StringUtil.isNotNullOrEmpty(ncd.getTime())) {
//				ncd.setTime(StringUtil.getShortTime(Long.parseLong(ncd.getTime())));
//			}
//			noteCommentQueryVo.setCommentId(ncd.getId());
//			noteCommentQueryVo.setUserId(SpringContextUtil.getUserId());
//			PageHelper.startPage(1, 4);	
//			// 查询回复
//			List<NoteCommentApplyDto> _list = commentMapper.apply(noteCommentQueryVo);
//			_list.stream().forEach(item->{
//				item.setTime(StringUtil.getShortTime(Long.parseLong(item.getTime())));
//			});
//			Page<NoteCommentApplyDto> _pageInfo = new Page<NoteCommentApplyDto>(_list, 1, 4);
//			ncd.setApplies(_pageInfo);
//		}
//		// 封装好评论（回复）
//		Page<NoteCommentDto> pageInfo = new Page<NoteCommentDto>(list, 1, 4);
//		ndd.setComments(pageInfo);
		// 详情每加载一次，帖子的浏览数增加
		System.out.println(ndd);
		return new Msg<>(ndd);
	}

	/**
	 * 删除当前用户的帖子
	 */
	@Override
	public Msg<?> delete(Long id) {
		// TODO Auto-generated method stub
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		noteMapper.delete(userId, id);
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
		List<NoteCommentDto> parentList = commentMapper.list1(noteId);
		// 只查询回复
		List<NoteCommentDto> childrenList = commentMapper.list2(noteId);

		for (NoteCommentDto parent : parentList) {
			for (NoteCommentDto children : childrenList) {
				if (children.getApplyId() == parent.getId()) {
					// 加一条子集就退出本轮
					List<NoteCommentDto> list = new ArrayList<NoteCommentDto>();
					list.add(children);
					// 默认丢一条
//					break;
				}
			}
		}
		Page<NoteCommentDto> pageList = new Page<>(parentList);
		return new Msg<>(pageList);
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

}
