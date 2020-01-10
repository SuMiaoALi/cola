package com.baoyun.ins.mapper.note.manager;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.note.dto.ManagerNoteQueryDto;
import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.vo.ManagerNoteQueryVo;

public interface ManagerNoteManager {

	/**
	 * @Description: 查询帖子列表
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param noteQueryVo
	 * @return
	 */
	@Select({
		"<script>",
		" SELECT n.id, n.title, n.cover, IFNULL(n.comment_count, 0) as commentCount, IFNULL(n.like_count, 0) as likeCount, n.author,",
		" IFNULL(n.collection_count, 0) collectionCount, IFNULL(n.view_count, 0) viewCount, n.status, n.is_hot as isHot, a.type as authorType,",
		" p.`name` authorName, p.photo, FROM_UNIXTIME(n.publish_time,'%Y-%m-%d %H:%i:%s') publishTime, n.is_delete isDelete, n.is_draft isDraft ",
		", (select content from t_note_content where note_id = n.id) content " +
		", c.id tagId, c.tag tagName " +
		" FROM t_note n " +
		"LEFT JOIN t_profile p ON n.author = p.user_id " +
		"LEFT JOIN t_auth_user a ON n.author = a.id ",
		"LEFT join t_note_tag b on b.note_id = n.id " +
		"LEFT JOIN t_bi_tag c on c.id = b.tag_id " +
		" WHERE 1=1 ",
		" <when test = 'vo.isHot != null'> and n.is_hot = #{vo.isHot}</when>",
		" <when test = 'vo.authorType !=null'> and a.type = #{vo.authorType}</when>",
		" <when test = 'vo.id != null'> and n.id = #{vo.id} </when>",
		" <when test = 'vo.noteName != null'> and n.title like '%${vo.noteName}%' </when>",
		" <when test = 'vo.author != null'> and p.name like '%${vo.author}%' </when>", 
		" <when test = 'vo.userId != null'> and n.author = #{vo.userId} </when>", 
		" <when test = 'vo.tagId != null and vo.tagId > 0'> and c.id = #{vo.tagId} </when>" +
		" <if test='vo.status !=null and vo.status !=\"\" and vo.status.indexOf(\"-2\") == -1 and vo.status.indexOf(\"-1\") == -1 '>" +
		"and n.is_draft = 1 and n.is_delete = 0 and n.status in(${vo.status}) </if> ",  
		" <if test='vo.status !=null and vo.status.indexOf(\"-2\") != -1 '> and n.is_delete = 1 </if>",  
		" <if test='vo.status !=null and vo.status.indexOf(\"-1\") != -1 '> and n.is_draft = 0 and n.is_delete = 0 </if>", 
		"<choose>",
		" <when test='vo.property !=null and vo.order != null' > ORDER BY  ${vo.property} ${vo.order}</when>", 
		" <otherwise> ORDER BY n.id desc </otherwise>",
		"</choose>",
		"</script>"
		})
	List<ManagerNoteQueryDto> list(@Param("vo") ManagerNoteQueryVo noteQueryVo);
	
	/**
	 * @Description: 查询帖子详情
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.like_count, 0) as likeCount, n.author, ",
		"ifnull(comment_count, 0) commentCount, ",
		"ifnull(n.collection_count, 0) as collectionCount, ifnull(n.share_count, 0) as shareCount, ",
		"ifnull(view_count, 0) as viewCount, p.`name`, p.photo, DATE_FORMAT(FROM_UNIXTIME(publish_time),'%Y-%m-%d %h:%i') as time, ",
		"(select content from t_note_content where note_id = n.id) as content, ", 
		"(select group_concat(tag) from t_bi_tag where id = c.tag_id) as tags " +
		" FROM t_note n LEFT JOIN t_profile p ON n.author = p.user_id " +
		"left join t_note_tag c on c.note_id = n.id " + 
		" WHERE n.id = #{id} ",
		"</script>"
		})
	NoteDetailDto get(@Param("id") long id);
	
//	/**
//	 * 查询帖子的基本信息
//	 * @author cola
//	 * @param id
//	 */
//	@Select("SELECT" + 
//			   " a.`name` AS authorName," + 
//			   " a.photo," + 
//			   " a.user_id AS authorId," + 
//			   " a.fans," + 
//			   " b.id AS noteId," + 
//			   " b.title," + 
//			   " IFNULL(b.collection_count, 0) collectionCount," + 
//			   " b.STATUS," + 
//			   " b.is_delete AS isDelete, " + 
//			   " (select count(1) from t_comment where note_id = #{id}) commentCount," + 
//			   " FROM_UNIXTIME( b.create_time, '%Y-%m-%d %H:%i:%s' ) AS createTime," + 
//			   " FROM_UNIXTIME( b.publish_time, '%Y-%m-%d %H:%i:%s' ) AS publishTime," + 
//			   " IFNULL(b.like_count, 0) likeCount," + 
//			   " IFNULL(b.view_count, 0) viewCount," + 
//			   " ( SELECT type FROM t_auth_user c WHERE c.id = a.user_id ) AS authorType," + 
//			   " ( SELECT content FROM t_note_content d WHERE d.note_id = b.id ) AS noteContent" + 
//			   " FROM" + 
//			   " t_profile a" + 
//			   " LEFT JOIN t_note b ON a.user_id = b.author " +
//			   "where b.id = #{id} "
//			   )
//	NoteInfoDto info(@Param("id") long id);
//	
	/**
	 * @Description: 彻底删除文章
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 */
	@Delete(" delete t5, t7, t8, t9, t10, t11, t2, t1, t13 from t_note_content t2 " + 
			" left join t_comment t13 on t13.note_id = t2.note_id " +
			" left join t_note_media t5 on t5.note_id = t2.note_id" + 
			" left join t_note_tag t7 on t7.note_id = t2.note_id" + 
			" left join t_log_note_like t8 on t8.note_id = t2.note_id " + 
			" left join t_log_note_view t9 on t9.note_id = t2.note_id " + 
			" left join t_log_note_collection t10 on t10.note_id = t2.note_id "+
			" left join t_log_note_share t11 on t11.note_id = t2.note_id "+
			" left join t_note t1 on t2.note_id = t1.id " + 
			" where t2.note_id = #{id}" 
	)
	void deleteComplete(long id);

	/**
	 * @Description: 获取帖子的标签
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 * @return
	 */
	@Select("select group_concat(tag) from t_bi_tag a left join t_note_tag b on b.tag_id = a.id where b.note_id = #{0} ")
	String getTags(Long id);

	/**
	 * @Description: 获取帖子图片
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param id
	 * @return
	 */
	@Select("select group_concat(url) from t_note_media where note_id = #{id} and status = 0 ")
	String getImg(long id);
	
	
}
