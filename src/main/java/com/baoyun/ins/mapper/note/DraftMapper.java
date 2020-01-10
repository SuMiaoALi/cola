package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.note.dto.DraftDto;
import com.baoyun.ins.entity.note.dto.DraftQueryDto;
import com.baoyun.ins.entity.note.vo.DraftVo;

/**
 * 
 * @author xiewei
 *
 */
public interface DraftMapper {
	
	/**
	 * 查询笔记
	 * @param noteQueryVo
	 * @return
	 */
	@Select("select n.id,n.title,c.description as content,n.cover,DATE_FORMAT(FROM_UNIXTIME(n.create_time),'%Y-%m-%d %H:%i') as time "
			+"from t_note n,t_note_content c where n.author = #{userId} and n.is_draft = 0 and n.is_delete = 0 and n.id = c.note_id order by n.create_time desc")
	List<DraftQueryDto> list(@Param("userId")String userId);
	
	/**
	 * 查看草稿箱
	 * @param id
	 * @param userId
	 * @return
	 */
	@Select("select n.id,n.title,c.draft as content,n.cover,"
			+"(select GROUP_CONCAT(tag) from t_note_tag where note_id = n.id group by note_id) as tags "
			+"from t_note n,t_note_content c "
			+"where n.id = c.note_id and n.id = #{id} and n.author = #{userId}")
	DraftDto get(@Param("id")long id,@Param("userId")String userId);
	
	/**
	 * 修改草稿件
	 * @param draftVo
	 */
	@Update({
		"<script>",
		"update t_note_draft set id=id ",
		" <when test='title != null '> ,title=#{title}</when> ",
		" <when test='content != null '> ,content=#{content}</when> ",
		" <when test='cover != null '> ,cover=#{cover}</when> ",
		" where id = #{id} and author = #{userId}",
		"</script>"
	})
	void update(DraftVo draftVo);
	
	/**
	 * 保存草稿箱
	 * @param noteVo
	 */
	@Insert("insert into `t_note_draft` ( `author`, `title`,`cover`, `create_time`, `content`, `is_read`)  values "+
	"(#{author},0,#{title},#{cover},UNIX_TIMESTAMP(NOW()),#{content},0)")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	void save(DraftVo draftVo);
	
	/**
	 * 删除草稿箱
	 * @param id
	 * @param userId
	 */
	@Delete("update from t_note set is_delete = 1 where and id = #{id} and author = #{userId}")
	void delete(@Param("id")long id,@Param("userId")String userId);

}
