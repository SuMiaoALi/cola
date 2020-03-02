package com.baoyun.ins.service.note.impl.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoyun.ins.entity.note.dto.ManagerNoteQueryDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.vo.ManagerNoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteApproveVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;
import com.baoyun.ins.mapper.note.CommentMapper;
import com.baoyun.ins.mapper.note.manager.ManagerNoteManager;
import com.baoyun.ins.service.note.manager.ManagerNoteService;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;
import com.baoyun.ins.utils.spring.SpringContextUtil;
import com.baoyun.ins.utils.string.StringUtil;
import com.github.pagehelper.PageHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 后台帖子
 * @Author cola
 * @Date 2020年1月6日
 */

@Slf4j
@Service
public class ManagerNoteServiceImpl implements ManagerNoteService {

	@Autowired
	private ManagerNoteManager noteMapper;
	
	@Autowired
	private CommentMapper commentMapper;
	
	/**
	 *后台帖子列表
	 */
	@Override
	public Msg<?> list(ManagerNoteQueryVo noteQueryVo) {
		// TODO Auto-generated method stub
		PageHelper.startPage(noteQueryVo.getStart(), noteQueryVo.getPageSize());	
		List<ManagerNoteQueryDto> list = noteMapper.list(noteQueryVo);
		Page<ManagerNoteQueryDto> pageInfo = new Page<>(list);
		return new Msg<Page<ManagerNoteQueryDto>>(pageInfo);
	}

	/**
	 *彻底删除帖子
	 */
	@Override
	public Msg<?> delete(Long id) {
		// TODO Auto-generated method stub
		noteMapper.deleteComplete(id);
		return new Msg<>();
	}

	/**
	 *帖子详情
	 */
	@Override
	public Msg<?> get(Long id) {
		// TODO Auto-generated method stub
//		NoteDetailDto ndd = noteMapper.get(id);
//		if (ndd != null) {
//			String tags = noteMapper.getTags(id);
//			String imgs = noteMapper.getImg(id);		
//			ndd.setTags(tags);
//			ndd.setImgs(imgs);
//			// 查询日记评论
//			NoteCommentQueryVo noteCommentQueryVo = new NoteCommentQueryVo(); 
//			noteCommentQueryVo.setNoteId(id); 
//			List<NoteCommentDto> list = commentMapper.list(noteCommentQueryVo); 
//			List<NoteCommentDto> parentList = list.stream()
//					.filter(item -> StringUtil.isNullOrEmpty(item.getApplyId() == null ? "":item.getApplyId().toString()))
//					.map(item->{
//						item.setChildren(new ArrayList<NoteCommentDto>());
//						return item;
//					}).collect(Collectors.toList());
//			log.info(parentList.toString());
//			// 转换成列表
//			List<NoteCommentDto> childrenList = list.stream().filter(item -> StringUtil.isNotNullOrEmpty(item.getApplyId() == null ? "":item.getApplyId().toString())).collect(Collectors.toList());
//			 
//			for(NoteCommentDto i:childrenList) {
//				for(NoteCommentDto j:parentList) {
//					if(i.getApplyId() == j.getId()) {
//						j.getChildren().add(i);
//						break;
//					}
//				} 
//			};
//			log.info(parentList.toString());
//	//		noteCommentQueryVo.setNullApplyId(null);
//	//		list.stream().forEach(item -> { 
//	//				noteCommentQueryVo.setApplyId(item.getId());
//	//				item.setChildren(commentMapper.list(noteCommentQueryVo));
//	// 
//	//		}); 
//			ndd.setCommentList(parentList);
//		}
		return new Msg<>();
	}

	/**
	 *帖子内容审核
	 */
	@Override
	public Msg<?> approve(NoteApproveVo noteApproveVo) {
		// TODO Auto-generated method stub
//		noteApproveVo.setApprover(SpringContextUtil.getUserId());
		noteApproveVo.setApprover("addcd2394d354ebfbd8da62145d25df7");
		// 审核
		noteMapper.approve(noteApproveVo);
		// 修改审核状态
		noteMapper.updateStatus(noteApproveVo.getNoteId(), noteApproveVo.getStatus());
		return new Msg<>();
	}

}
