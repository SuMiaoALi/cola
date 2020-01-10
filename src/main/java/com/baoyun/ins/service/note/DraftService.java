package com.baoyun.ins.service.note;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.note.dto.DraftDto;
import com.baoyun.ins.entity.note.dto.DraftQueryDto;
import com.baoyun.ins.entity.note.vo.DraftVo;
import com.baoyun.ins.utils.json.Msg;
import com.baoyun.ins.utils.pagehelper.Page;

public interface DraftService {
	
	/**
	 * 添加草稿
	 * @param draftVo
	 * @return
	 */
	Msg<?> save(DraftVo draftVo);
	
	/**
	 * 修改草稿
	 * @param draftVo
	 * @return
	 */
	Msg<?> update(DraftVo draftVo);
	
	/**
	 * 查询草稿
	 * @param draftQueryVo
	 * @return
	 */
	Msg<Page<DraftQueryDto>> list(BaseVo baseVo);
	
	/**
	 * 查看草稿详情
	 * @param id
	 * @return
	 */
	Msg<DraftDto> get(long id);
	
	/**
	 * 删除草稿箱
	 * @param id
	 * @return
	 */
	Msg<?> delete(long id);

}
