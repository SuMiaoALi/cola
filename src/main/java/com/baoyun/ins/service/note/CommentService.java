package com.baoyun.ins.service.note;

import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.vo.CommentVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

public interface CommentService {
	
	/**
	 * 添加评论
	 * @param noteVo
	 * @return
	 */
	Msg<?> save(CommentVo commentVo);
	
	/**
	 * 查询评论
	 * @param noteQueryVo
	 * @return
	 */
	Msg<Page<NoteCommentDto>> list(NoteCommentQueryVo noteCommentQueryVo);
	
	/**
	 * 查看评论下的回复
	 * @param noteCommentQueryVo
	 * @return
	 */
	Msg<Page<NoteCommentApplyDto>> applies(NoteCommentQueryVo noteCommentQueryVo);

	/**
	 * @Description: 删除评论
	 * @Author cola
	 * @Data: 2020年3月8日
	 * @param id
	 * @return
	 */
	Msg<?> delete(Long id);
}
