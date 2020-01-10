package com.baoyun.ins.service.qe;

import com.baoyun.ins.entity.BaseVo;
import com.baoyun.ins.entity.qe.vo.QuesVo;
import com.baoyun.ins.utils.json.Msg;

/**
 * @Description: 问卷接口
 * @Author cola
 * @Date 2019年12月19日
 */
public interface QuesService {

	/**   
	* 保存问卷
	* @author: cola
	* @date: 2019年12月19日
	* @param: QuesVo
	* @return：
	*/
	Msg<?> save(QuesVo quesVo);

	/**
	 * @Description: 查询用户有没有问题
	 * @Author cola
	 * @param BaseVo
	 */
	Msg<?> hasQuestion(BaseVo base);

	/**
	 * @Description: 查询问题详情
	 * @Author cola
	 * @param id
	 */
	Msg<?> datail(String id);
}
