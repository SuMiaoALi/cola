package com.baoyun.ins.service.note;

import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

public interface NoteService {
	
	/**
	 * 添加笔记
	 * @param noteVo
	 * @return
	 */
	Msg<?> save(NoteVo noteVo);
	
	/**
	 * 查询笔记
	 * @param noteQueryVo
	 * @return
	 */
	Msg<Page<NoteQueryDto>> list(NoteQueryVo noteQueryVo);
	
	/**
	 * 对笔记进行操作
	 * @param id
	 * @param type：0点赞 1取消点赞 2收藏 3取消收藏 4分享 5查看
	 * @return
	 */
	Msg<?> operate(Long id, Integer type);
	
	/**
	 * 查看日记详情
	 * @param id
	 * @return
	 */
	Msg<NoteDetailDto> get(Long id);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	Msg<?> delete(Long id);

}
