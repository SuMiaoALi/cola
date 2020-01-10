package com.baoyun.ins.mapper.monitor;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.monitor.dto.InformDto;
import com.baoyun.ins.entity.monitor.vo.InformVo;

/**
 * 
 * @author xiewei
 *
 */
public interface InformMapper {
	
	/**
	 * 查询举报基础数据
	 * @param id
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT id,title,description,parent_id as parentId from t_bi_inform",
		"</script>"
		})
	List<InformDto> listBi();
	
	/**
	 * 举报
	 * @param informVo
	 */
	@Select(
			"insert into `t_inform` ( `status`, `content`, `imgs`, `user_id`, `object_id`, `type`, `inform_id`)"
			+"values ( 0, #{content}, #{images}, #{userId}, #{objectId}, #{type}, #{informId})")
	void save(InformVo informVo);
}
