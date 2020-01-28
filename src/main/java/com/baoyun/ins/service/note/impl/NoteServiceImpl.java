package com.baoyun.ins.service.note.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoyun.ins.entity.bi.vo.NoteTagVo;
import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.dto.NoteOperateDto;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;
import com.baoyun.ins.entity.note.vo.NoteImgVo;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.mapper.note.CommentMapper;
import com.baoyun.ins.mapper.note.KeywordMapper;
import com.baoyun.ins.mapper.note.NoteMapper;
import com.baoyun.ins.service.note.NoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.string.StringUtil;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 帖子接口
 * @Author cola
 * @Date 2020年1月6日
 */
@Service
@Slf4j
public class NoteServiceImpl implements NoteService {
	
	@Autowired
	private NoteMapper noteMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private KeywordMapper keywordMapper;
	
	/**
	 *发布帖子
	 */
	@Override
	@Transactional
	public Msg<?> save(NoteVo noteVo) {
		// TODO Auto-generated method stub
		Msg<Object> msg = new Msg<>();
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		noteVo.setIsDraft("1").setAuthor(userId).setCover(noteVo.getImages().get(0).getUrl()).setStatus("0");
		// 传0为发布帖子
		if(noteVo.getId() == 0) {
			noteMapper.save(noteVo);
			noteMapper.saveContent(noteVo);
			msg.setData(noteVo.getId());
		} else {
			// 修改帖子
			noteMapper.update(noteVo);
			noteMapper.updateContent(noteVo);
			noteMapper.clearImg(noteVo.getId());
		}
		// 添加图片
		for(NoteImgVo img : noteVo.getImages()) {
			img.setNoteId(noteVo.getId());
			noteMapper.addImg(img);
		}
		// 添加标签
		noteMapper.deleteTags(noteVo.getId());
		if(noteVo.getTags() != null && noteVo.getTags().size() > 0) {
			for(NoteTagVo tag : noteVo.getTags()) {
				tag.setNoteId(noteVo.getId());
				noteMapper.addTag(tag);
			}
		}
		return msg;
	}

	/**
	 *查询笔记列表
	 */
	@Override
	public Msg<Page<NoteQueryDto>> list(NoteQueryVo noteQueryVo) {
		// 保存用户关键词
		if(StringUtil.isNotNullOrEmpty(noteQueryVo.getKey()) && StringUtil.isNotNullOrEmpty(SpringContextUtil.getUserId())) {
			log.info(noteQueryVo.getKey());
			keywordMapper.save(noteQueryVo.getKey(), SpringContextUtil.getUserId());
		} 
		// 分页
		noteQueryVo.setUserId(SpringContextUtil.getUserId());
		PageHelper.startPage(noteQueryVo.getStart(), noteQueryVo.getPageSize());
		List<NoteQueryDto> list = noteMapper.list(noteQueryVo); 
		Page<NoteQueryDto> pageInfo = new Page<NoteQueryDto>(list, noteQueryVo.getStart(), noteQueryVo.getPageSize());
		return new Msg<Page<NoteQueryDto>>(pageInfo); 
	}

	/**
	 *点赞等操作
	 */
	@Override
	public Msg<?> operate(Long id, Integer type) {
		// TODO Auto-generated method stub
		Msg<?> msg = new Msg<>();
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		NoteOperateDto nod = noteMapper.operate(userId, id, type);
		msg.setCode(nod.getRs());
		return msg;
	}

	/**
	 *帖子详情
	 */
	@Override
	public Msg<NoteDetailDto> get(Long id) {
		// TODO Auto-generated method stub
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		NoteDetailDto ndd = noteMapper.get(id, userId);
		// 图片，标签
		ndd.setMedia(noteMapper.getImg(id));
		ndd.setTags(noteMapper.getTags(id));
		// 查询日记评论
		NoteCommentQueryVo noteCommentQueryVo = new NoteCommentQueryVo();
		noteCommentQueryVo.setUserId(userId);
		noteCommentQueryVo.setNoteId(id);
		PageHelper.startPage(1, 4);	
		// 日记评论列表
		List<NoteCommentDto> list = commentMapper.list(noteCommentQueryVo);
		
		for(NoteCommentDto ncd : list) {
			if(StringUtil.isNotNullOrEmpty(ncd.getTime())) {
				ncd.setTime(StringUtil.getShortTime(Long.parseLong(ncd.getTime())));
			}
			noteCommentQueryVo.setCommentId(ncd.getId());
			noteCommentQueryVo.setUserId(SpringContextUtil.getUserId());
			PageHelper.startPage(1, 4);	
			// 查询回复
			List<NoteCommentApplyDto> _list = commentMapper.apply(noteCommentQueryVo);
			_list.stream().forEach(item->{
				item.setTime(StringUtil.getShortTime(Long.parseLong(item.getTime())));
			});
			Page<NoteCommentApplyDto> _pageInfo = new Page<NoteCommentApplyDto>(_list, 1, 4);
			ncd.setApplies(_pageInfo);
		}
		// 封装好评论（回复）
		Page<NoteCommentDto> pageInfo = new Page<NoteCommentDto>(list, 1, 4);
		ndd.setComments(pageInfo);
		// 推荐帖
		ndd.setRecommend(noteMapper.recommend(id, userId));
		try {
			noteMapper.operate(userId, id, 5);
		} catch(Exception e) {
			e.printStackTrace();
		}
		// 详情每加载一次，帖子的浏览数增加
		ndd.setViewCount(ndd.getViewCount() + 1);
		return new Msg<>(ndd);
	}

	/**
	 *删除当前用户的帖子
	 */
	@Override
	public Msg<?> delete(Long id) {
		// TODO Auto-generated method stub
//		String userId = SpringContextUtil.getUserId();
		String userId = "addcd2394d354ebfbd8da62145d25df7";
		noteMapper.delete(userId, id);
		return new Msg<>();
	}

}
