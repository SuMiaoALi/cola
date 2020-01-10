package com.baoyun.ins.mapper.qe;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.qe.dto.QuesDetailDto;
import com.baoyun.ins.entity.qe.dto.QuestionListDto;
import com.baoyun.ins.entity.qe.vo.QuesVo;

/**
 * @Description: 问卷持久化
 * @Author cola
 * @Date 2019年12月19日
 */
public interface QuesMapper {

	/**
	 * @Description: 保存问卷 
	 * @Author cola
	 * @Data: 2019年12月19日
	 * @param quesVo
	 */
	@Insert("insert into t_que_info(form_id, sex, age, stage, description, picture, user_id, status, batch_id, create_time) " +
			"values (#{formId}, #{sex}, #{age}, #{stage}, #{description}, #{picture}, #{userId}, '0', #{batchId}, unix_timestamp(now())) ")
	@Options(useGeneratedKeys = true, keyColumn = "id")
	void save(QuesVo quesVo);

	/**
	 * @Description: 查询用户有没有问题
	 * @Author cola
	 * @return
	 * @throws 
	 */
	@Select("select 1 from t_que_info where user_id = #{0} limit 1 ")
	Integer hasQuestion(String userId);

	/**
	 * @Description: 查询用户的问题列表-我的问题页
	 * @Author cola
	 * @param userId
	 * @throws 
	 */
	@Select("select id, description, status, from_unixtime(create_time, '%Y-%m-%d  %H:%i') createTime from t_que_info where user_id = #{0} order by create_time desc ")
	List<QuestionListDto> getQuestionList(String userId);

	/**
	 * @Description: 查询问题详情
	 * @Author cola
	 * @param id
	 * @throws 
	 */
	@Select("select a.id, sex, age, stage, status, description, from_unixtime(a.create_time, '%Y-%m-%d  %H:%i') createTime , b.answer from t_que_info a " + 
			"left join t_que_batch b on b.id = a.batch_id " + 
			"where a.id = #{0} ")
	QuesDetailDto detail(String id);
	
}
