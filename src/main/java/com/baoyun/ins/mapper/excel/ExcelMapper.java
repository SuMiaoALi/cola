package com.baoyun.ins.mapper.excel;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.excel.dto.ExcelTestDto;
import com.baoyun.ins.entity.excel.vo.ExcelTestVo;

@Mapper
public interface ExcelMapper {

	/**
	 * @Description: 上传表格
	 * @Author cola
	 * @Data: 2020年3月31日
	 * @param list
	 */
	@Insert({
		"<script>" +
		"insert into t_excel(time, new_patient, new_death) values " +
		"<foreach collection = 'list' item = 'item' separator = ','>"
		+ "(unix_timestamp(#{item.time}), #{item.newPatient}, #{item.newDeath})"
		+ "</foreach>"
		+ "</script>"
	})
	void upload(List<ExcelTestVo> list);

	/**
	 * @Description: 查询数据, 开窗函数累加over
	 * @Author cola
	 * @Data: 2020年3月31日
	 * @return
	 */
	@Select("select from_unixtime(time, '%m-%d') time, new_patient newPatient, new_death newDeath, "
			+ "sum(new_patient) over(order by FROM_UNIXTIME(time, '%m-%d')) totalNewPatient, " + 
			"sum(new_death) over(order by FROM_UNIXTIME(time, '%m-%d')) totalNewDeath from t_excel order by time asc ")
	List<ExcelTestDto> get();

}
