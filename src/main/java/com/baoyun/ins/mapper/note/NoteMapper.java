package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.bi.vo.NoteTagVo;
import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.dto.NoteMediaDto;
import com.baoyun.ins.entity.note.dto.NoteOperateDto;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
import com.baoyun.ins.entity.note.vo.NoteImgVo;
import com.baoyun.ins.entity.note.vo.NoteQueryVo;
import com.baoyun.ins.entity.note.vo.NoteVo;

/**
 * @Description: 帖子接口
 * @Author cola
 * @Date 2020年1月3日
 */
public interface NoteMapper {
	
	/**
	 * @Description: 查询帖子列表
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteQueryVo
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author, IFNULL(n.collection_count, 0) collectionCount,",
		"ifnull(n.view_count, 0) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{vo.userId} and note_id = n.id) as isLiked ",
		"FROM t_note n, t_profile p, t_note_content c ",
		"<when test = 'vo.tagId != null and vo.tagId != \"\"'> ,(SELECT note_id FROM t_note_tag WHERE tag_id = #{vo.tagId} ) t </when>",
		"WHERE n.author = p.user_id and n.is_draft = 1 and n.is_delete = 0 and n.status = 1 and n.id = c.note_id ",
		" <when test='vo.tagId !=null and vo.tagId != \"\"'> and t.note_id = n.id </when>",
		" <when test='vo.key != null'> and n.title like '%${vo.key}%' </when>",
		" <when test='vo.author != null'> and n.author = #{vo.author} </when>",
		"ORDER BY n.is_hot desc, publish_time desc",
		"</script>"
		})
	List<NoteQueryDto> list(@Param("vo")NoteQueryVo noteQueryVo);
	
	/**
	 * @Description: 推荐帖子4篇
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @param userId
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author,",
		// 当前用户是否点赞
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{userId} and status = 0 and note_id = n.id) as isLiked,",
		"p.`name`, p.photo ",
		"FROM t_note n, t_profile p WHERE n.author = p.user_id and n.id != #{id} and n.is_draft = 1 and n.status = 1 and n.is_delete = 0 ",
		"ORDER BY n.publish_time desc limit 4",
		"</script>"
		})
	List<NoteQueryDto> recommend(@Param("id")long id, @Param("userId")String userId);
	
	/**
	 * @Description: 我收藏的
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param userId
	 * @param isMine
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.title, n.cover, n.author, IFNULL(n.comment_count, 0) as commentCount, IFNULL(n.like_count, 0) as likeCount, IFNULL(n.collection_count,0) collectionCount,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{userId} and status = 0 and note_id = n.id) as isLiked,",
		"p.`name`,p.photo,n.is_delete as isDelete ",
		"FROM t_note n,t_profile p,t_log_note_collection c ",
		"WHERE n.author = p.user_id and c.note_id = n.id and c.collector = #{userId} and c.status = 0 and n.status = 1 and n.type = 'text' ",
		" <when test='isMine == null'> and n.is_delete = 0 </when>",
		"ORDER BY publish_time desc",
		"</script>"
		})
	List<NoteQueryDto> collection(@Param("userId")String userId, @Param("isMine")String isMine);
	
	/**
	 * @Description: 查看日记详情
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @param userId
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.title, n.cover, IFNULL(n.comment_count, 0) as commentCount, IFNULL(n.like_count, 0) as likeCount, n.author,",
		// 是否是自己的帖子
		"(select case when count(1) > 0 then 1 else 0 end from t_note where id = #{id} and author = #{userId}) as isMine, IFNULL(n.collection_count, 0) as collectionCount,",
		"IFNULL(view_count, 0) as viewCount, p.`name`, p.photo, DATE_FORMAT(FROM_UNIXTIME(publish_time), '%Y-%m-%d') as time, ",
		"(select content from t_note_content where note_id = n.id) as content, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_fans where fans_id = #{userId} and is_delete = 0 and user_id = n.author) as isFollow,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{userId} and note_id = n.id) as isLiked,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_share where sharer = #{userId} and note_id = n.id) as isShared,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{userId} and note_id = n.id) as isCollected,",
		// 是否互相关注
		"(SELECT is_mutual FROM t_fans WHERE user_id = #{userId} and fans_id = n.author and is_delete = 0) as isAttention ",
		"FROM t_note n, t_profile p WHERE n.author = p.user_id and n.id = #{id}",
		"</script>"
	})
	NoteDetailDto get(@Param("id")Long id, @Param("userId")String userId);
	
	/**
	 * @Description: 查询帖子图片
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @return
	 */
	@Select("select url from t_note_media where note_id = #{id} and status = 0 ")
	List<NoteMediaDto> getImg(@Param("id")long id);
	
	/**
	 * @Description: 查询帖子标签
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @return
	 */
	@Select("select group_concat(a.tag) from t_bi_tag a inner join t_note_tag b on b.tag_id = a.id " + 
			"where b.note_id = #{id}")
	String getTags(@Param("id")Long id);
	
	/**
	 * @Description: 修改帖子
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteVo
	 */
	@Update({
		"<script>",
		"update t_note set id=id ",
		" <when test = 'isDraft != null'> , is_draft = #{isDraft} </when> ",
		" <when test = 'title != null'> , title = #{title} </when> ",
		" <when test = 'cover != null'> , cover = #{cover} </when> ",
		" where id = #{id} and author = #{author} ",
		"</script>"
	})
	void update(NoteVo noteVo);
	
	/**
	 * @Description: 添加笔记
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteVo
	 */
	@Insert("insert into `t_note` (`comment_count`, `author`, `collection_count`, `title`, `is_delete`, `like_count`, `cover`, `publish_time`, `create_time`, `share_count`, `status`, is_draft) "+
	"values (0, #{author}, 0, #{title}, 0, 0, #{cover}, UNIX_TIMESTAMP(NOW()), UNIX_TIMESTAMP(NOW()), 0, #{status}, #{isDraft})")
	@Options(useGeneratedKeys = true, keyColumn = "id")
	void save(NoteVo noteVo);
	
	/**
	 * @Description: 添加笔记内容
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteVo
	 */
	@Insert("insert into `t_note_content` (`note_id`, `content`)  values (#{id}, #{content})")
	void saveContent(NoteVo noteVo);
	
	/**
	 * @Description: 更新笔记内容
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteVo
	 */
	@Update({
		"<script>",
		"update t_note_content set note_id = note_id ",
		" <when test = 'content != null and content != \"\"'> ,content = #{content} </when> ",
		" where note_id = #{id}",
		"</script>"
	})
	void updateContent(NoteVo noteVo);
	
	/**
	 * 执行帖子的操作点赞，存储过程
	 * @param userId
	 * @param id
	 * @param type
	 * @return
	 */
	@Select("call p_operate_note(#{userId}, #{id}, #{type})")
	NoteOperateDto operate(@Param("userId")String userId, @Param("id")Long id, @Param("type")Integer type);
	
	/**
	 * 删除帖子，状态 = 1
	 * @param userId
	 * @param id
	 */
	@Delete("update t_note set is_delete = 1 where author = #{userId} and id = #{id}")
	void delete(@Param("userId")String userId, @Param("id")Long id);

	/**
	 * 删除所有标签
	 * @param id
	 */
	@Delete("delete from t_note_tag where note_id = #{id} ")
	void deleteTags(@Param("id")long id);
	
	/**
	 * 保存标签
	 * @param id
	 * @param tag
	 */
	@Insert("insert into t_note_tag(note_id, tag_id) values (#{noteId}, #{tagId} )")
	void addTag(NoteTagVo tag);
	
	/**
	 * 清除帖子下的图片
	 * @param id
	 */
	@Update("update t_note_media set status = 1 where note_id = #{id}")
	void clearImg(@Param("id")long id);
	
	/**
	 * 添加图片
	 * @param img
	 */
	@Insert("insert into t_note_media(note_id, url, status) values (#{noteId}, #{url}, 0)")
	void addImg(NoteImgVo img);
	
	
}
