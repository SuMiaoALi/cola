package com.baoyun.ins.service.note.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.vo.CommentVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;
import com.baoyun.ins.mapper.note.CommentMapper;
import com.baoyun.ins.service.note.CommentService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.string.StringUtil;
import com.github.pagehelper.PageHelper;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;

	@Override
	@Transactional
	public Msg<?> save(CommentVo commentVo) {
		// TODO Auto-generated method stub
		commentVo.setCommenter(SpringContextUtil.getUserId());
		commentMapper.save(commentVo);
		return new Msg<>();
	}

	@Override
	public Msg<Page<NoteCommentDto>> list(NoteCommentQueryVo noteCommentQueryVo) {
		// TODO Auto-generated method stub
		noteCommentQueryVo.setUserId(SpringContextUtil.getUserId());
		PageHelper.startPage(noteCommentQueryVo.getStart(), noteCommentQueryVo.getPageSize());	
		List<NoteCommentDto> list = commentMapper.list(noteCommentQueryVo);
		for(NoteCommentDto ncd : list) {
			if(StringUtil.isNotNullOrEmpty(ncd.getTime())) {
				ncd.setTime(StringUtil.getShortTime(Long.parseLong(ncd.getTime())));
			}
			noteCommentQueryVo.setUserId(SpringContextUtil.getUserId());
			noteCommentQueryVo.setCommentId(ncd.getId());
			PageHelper.startPage(1, 4);	
			List<NoteCommentApplyDto> _list = commentMapper.apply(noteCommentQueryVo);
			_list.stream().forEach(item->{
				item.setTime(StringUtil.getShortTime(Long.parseLong(item.getTime())));
			});
			Page<NoteCommentApplyDto> _pageInfo = new Page<NoteCommentApplyDto>(_list,1, 4);
			ncd.setApplies(_pageInfo);
		}
		Page<NoteCommentDto> pageInfo = new Page<NoteCommentDto>(list,noteCommentQueryVo.getStart(), noteCommentQueryVo.getPageSize());
		return new Msg<Page<NoteCommentDto>>(pageInfo); 
	}

	@Override
	public Msg<Page<NoteCommentApplyDto>> applies(NoteCommentQueryVo noteCommentQueryVo) {
		// TODO Auto-generated method stub
		noteCommentQueryVo.setUserId(SpringContextUtil.getUserId());
		PageHelper.startPage(noteCommentQueryVo.getStart(), noteCommentQueryVo.getPageSize());
		List<NoteCommentApplyDto> _list = commentMapper.apply(noteCommentQueryVo);
		Page<NoteCommentApplyDto> _pageInfo = new Page<NoteCommentApplyDto>(_list,noteCommentQueryVo.getStart(), noteCommentQueryVo.getPageSize());
		return new Msg<Page<NoteCommentApplyDto>>(_pageInfo); 
	}


}
