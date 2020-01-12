package com.baoyun.ins.service.note.manager;

import com.baoyun.ins.entity.note.vo.ManagerNoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteApproveVo;
import com.baoyun.ins.utils.json.Msg;

public interface ManagerNoteService {

	/**
	 * @Description: 后台笔记列表
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param noteQueryVo
	 * @return
	 */
	Msg<?> list(ManagerNoteQueryVo noteQueryVo);

	/**
	 * @Description: 彻底删除笔记
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 * @return
	 */
	Msg<?> delete(Long id);

	/**
	 * @Description: 查询笔记详情
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 * @return
	 */
	Msg<?> get(Long id);

	/**
	 * @Description: 帖子审核
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param noteApproveVo
	 * @return
	 */
	Msg<?> approve(NoteApproveVo noteApproveVo);

}
