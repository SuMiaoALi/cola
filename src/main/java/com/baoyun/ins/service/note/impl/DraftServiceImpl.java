package com.baoyun.ins.service.note.impl;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.bi.vo.NoteTagVo;
import com.baoyun.ins.entity.note.dto.DraftDto;
import com.baoyun.ins.entity.note.dto.DraftQueryDto;
import com.baoyun.ins.entity.note.vo.DraftVo;
import com.baoyun.ins.entity.note.vo.NoteImgVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.mapper.note.DraftMapper;
import com.baoyun.ins.mapper.note.NoteMapper;
import com.baoyun.ins.service.note.DraftService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.string.StringUtil;
import com.github.pagehelper.PageHelper;

/**
 * @Description: 草稿箱接口
 * @Author cola
 * @Date 2020年1月6日
 */

@Service
public class DraftServiceImpl implements DraftService {
	
	@Autowired
	private DraftMapper draftMapper;
	
	@Autowired
	private NoteMapper noteMapper;
	
	@Override
	@Transactional
	public Msg<?> save(DraftVo draftVo) {
		// TODO Auto-generated method stub
		NoteVo noteVo = new NoteVo();
		if(StringUtil.isNotNullOrEmpty(draftVo.getContent())) {
			Document doc = Jsoup.parse(draftVo.getContent());
			noteVo.setDescription(doc.text());
		}
		noteVo.setTitle(draftVo.getTitle());
		noteVo.setContent(draftVo.getContent());
		noteVo.setCover(draftVo.getCover());
		noteVo.setIsDraft("0");
		noteVo.setAuthor(SpringContextUtil.getUserId());
		// 设置封面
		if(draftVo.getImages() != null && draftVo.getImages().size() > 0) {
			noteVo.setCover(draftVo.getImages().get(0).getUrl());
		}
		// 保存内容
		if(draftVo.getId() == 0) {
			noteMapper.save(noteVo);
			noteMapper.saveContent(noteVo);
		}else {
			noteVo.setId(draftVo.getId());
			noteMapper.update(noteVo);
			noteMapper.updateContent(noteVo);
			noteMapper.clearImg(noteVo.getId());
		}
		// 设置图片
		if(draftVo.getImages() != null && draftVo.getImages().size() > 0) {
			for(NoteImgVo img : draftVo.getImages()) {
				img.setNoteId(noteVo.getId());
				noteMapper.addImg(img);
			}
		}
		// 设置标签
 		noteMapper.deleteTags(noteVo.getId());
		if(draftVo.getTags() != null && draftVo.getTags().size() > 0) {
			for(NoteTagVo tag : draftVo.getTags()) {
				tag.setNoteId(noteVo.getId());
				noteMapper.addTag(tag);
			}
		}
		return new Msg<>();
	}

	@Override
	public Msg<Page<DraftQueryDto>> list(BaseVo baseVo) {
		// TODO Auto-generated method stub
		PageHelper.startPage(baseVo.getStart(), baseVo.getPageSize());	
		List<DraftQueryDto> list = draftMapper.list(SpringContextUtil.getUserId()); 
		Page<DraftQueryDto> pageInfo = new Page<DraftQueryDto>(list,baseVo.getStart(), baseVo.getPageSize());
		return new Msg<Page<DraftQueryDto>>(pageInfo); 
	}

	@Override
	public Msg<DraftDto> get(long id) {
		// TODO Auto-generated method stub
		return new Msg<>(draftMapper.get(id, SpringContextUtil.getUserId()));
	}

	@Override
	public Msg<?> update(DraftVo draftVo) {
		// TODO Auto-generated method stub
		draftVo.setAuthor(SpringContextUtil.getUserId());
		draftMapper.update(draftVo);
		return new Msg<>();
	}

	@Override
	public Msg<?> delete(long id) {
		// TODO Auto-generated method stub
		noteMapper.delete(SpringContextUtil.getUserId(),id);
		return new Msg<>();
	}

}
