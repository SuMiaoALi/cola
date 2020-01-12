package com.baoyun.ins.service.tag.manager;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.tag.vo.TagVo;
import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 后台标签接口
 * @Author cola
 * @Date 2020年1月12日
 */
public interface ManagerTagService {

	/**
	 * @Description: 获取标签列表
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param base
	 * @return
	 */
	Msg<?> list(BaseVo base);

	/**
	 * @Description: 添加标签
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param tagVo
	 * @return
	 */
	Msg<?> insert(TagVo tagVo);

	/**
	 * @Description: 删除标签
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param id
	 * @return
	 */
	Msg<?> delete(Integer id);

	/**
	 * @Description: 修改标签
	 * @Author cola
	 * @Data: 2020年1月12日
	 * @param tagVo
	 * @return
	 */
	Msg<?> update(TagVo tagVo);

}
