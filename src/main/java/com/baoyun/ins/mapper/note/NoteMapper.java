package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.baoyun.ins.entity.note.dto.NoteDetailDto;
import com.baoyun.ins.entity.note.dto.NoteOperateDto;
import com.baoyun.ins.entity.note.dto.NoteQueryDto;
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
		"(select count(*) from t_log_note_view where note_id = n.id) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{vo.userId} and note_id = n.id) as isLiked, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{vo.userId} and note_id = n.id) as isCollected ",
		"FROM t_note n, t_profile p, t_note_content c ",
		"<when test = 'vo.tagId != null and vo.tagId != \"\" and vo.tagId != 0'> ,(SELECT note_id FROM t_note_tag WHERE tag_id = #{vo.tagId} ) t </when>",
		"WHERE n.author = p.user_id and n.is_delete = 0 and n.status = 1 and n.id = c.note_id ",
		"<when test='vo.tagId == 0'> and n.is_hot = 1 </when>",
		"<when test='vo.tagId !=null and vo.tagId != \"\" and vo.tagId != 0'> and t.note_id = n.id </when>",
		"<when test='vo.key != null'> and n.title like '%${vo.key}%' </when>",
		"<when test='vo.info != null and vo.info != \"\"'> and n.is_info = #{vo.info} </when>",
		"<when test='vo.author != null and vo.author != \"\"'> and n.author = #{vo.author} </when>",
		"<when test='vo.userId != null and vo.userId != \"\"'> and n.id not in (select note_id from t_note_shield where user_id = #{vo.userId}) </when>",
		"ORDER BY n.is_hot desc, n.create_time desc",
		"</script>"
		})
	List<NoteQueryDto> list(@Param("vo") NoteQueryVo noteQueryVo);
	
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
		"SELECT n.id, n.title, n.cover, ifnull(n.like_count, 0) likeCount, IFNULL(n.collection_count, 0) collectionCount, ",
		"(select count(*) from t_log_note_view where note_id = n.id) as viewCount, n.author,",
		// 当前用户是否点赞
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{userId} and note_id = n.id) as isLiked,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{userId} and note_id = n.id) as isCollected,",
		"p.`name`, p.photo ",
		"FROM t_note n, t_profile p WHERE n.author = p.user_id and n.id != #{id} and n.status = 1 and n.is_delete = 0 ",
		"ORDER BY n.create_time desc limit 4",
		"</script>"
		})
	List<NoteQueryDto> recommend(@Param("id") long id, @Param("userId")String userId);
	
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
		"SELECT n.id, n.title, IFNULL(n.comment_count, 0) as commentCount, IFNULL(n.like_count, 0) as likeCount, n.author, n.cover, ",
		// 是否是自己的帖子
		"(select case when count(1) > 0 then 1 else 0 end from t_note where id = #{id} and author = #{userId}) as isMine, IFNULL(n.collection_count, 0) as collectionCount, ",
		"p.`name`, p.photo, p.description, DATE_FORMAT(FROM_UNIXTIME(create_time), '%Y-%m-%d   %H:%i') as time, ",
		"(select count(*) from t_log_note_view where note_id = n.id) as viewCount, ",
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
	NoteDetailDto get(@Param("id") Long id, @Param("userId") String userId);
	
	/**
	 * @Description: 查询帖子图片
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @return
	 */
	@Select("select url from t_note_media where note_id = #{id} ")
	List<String> getImg(@Param("id") long id);
	
	/**
	 * @Description: 查询帖子标签
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param id
	 * @return
	 */
	@Select("select group_concat(a.tag) from t_bi_tag a inner join t_note_tag b on b.tag_id = a.id " + 
			"where b.note_id = #{id}")
	String getTags(@Param("id") Long id);
	
	/**
	 * @Description: 添加笔记
	 * @Author cola
	 * @Data: 2020年1月3日
	 * @param noteVo
	 */
	@Insert("insert into `t_note` (`comment_count`, `author`, `collection_count`, `title`, `is_delete`, `like_count`, `cover`, `create_time`, `share_count`, `status`, `is_hot`, `is_info`) "+
	"values (0, #{author}, 0, #{title}, #{delete}, 0, #{cover}, UNIX_TIMESTAMP(NOW()), 0, #{status}, #{hot}, 0)")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
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
	@Delete("update t_note set is_delete = 1 where author = #{0} and id = #{1}")
	void delete(String userId, Long id);

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
	@Insert("insert into t_note_tag(note_id, tag_id) values (#{0}, #{1} )")
	void addTag(Integer noteId, Integer tagId);
	
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
	@Insert("insert into t_note_media(note_id, url) values (#{0}, #{1})")
	void addImg(Integer noteId, String url);

	/**
	 * @Description: 我发布的
	 * @Author cola
	 * @Data: 2020年3月1日
	 * @param userId
	 * @return
	 */
	@Select({"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author, IFNULL(n.collection_count, 0) collectionCount,",
		"ifnull(n.view_count, 0) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{0} and note_id = n.id) as isLiked, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{0} and note_id = n.id) as isCollected ",
		"FROM t_note n, t_profile p, t_note_content c ",
		"WHERE n.author = p.user_id and n.is_delete = 0 and n.status = 1 and n.id = c.note_id and n.author = #{0} ",
		"ORDER BY n.is_hot desc, n.create_time desc",
		"</script>"
		})
	List<NoteQueryDto> mine(String userId);

	/**
	 * @Description: 我收藏的
	 * @Author cola
	 * @Data: 2020年3月1日
	 * @param userId
	 * @return
	 */
	@Select({"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author, IFNULL(n.collection_count, 0) collectionCount,",
		"ifnull(n.view_count, 0) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{0} and note_id = n.id) as isLiked, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{0} and note_id = n.id) as isCollected ",
		"FROM t_note n, t_profile p, t_note_content c, t_log_note_collection lnc ",
		"WHERE n.author = p.user_id and n.is_delete = 0 and n.status = 1 and n.id = c.note_id and n.id = lnc.note_id and lnc.collector = #{0} ",
		"ORDER BY n.is_hot desc, n.create_time desc",
		"</script>"
		})
	List<NoteQueryDto> myCollection(String userId);

	/**
	 * @Description: 我喜欢的
	 * @Author cola
	 * @Data: 2020年3月1日
	 * @param userId
	 * @return
	 */
	@Select({"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author, IFNULL(n.collection_count, 0) collectionCount,",
		"ifnull(n.view_count, 0) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{0} and note_id = n.id) as isLiked, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{0} and note_id = n.id) as isCollected ",
		"FROM t_note n, t_profile p, t_note_content c, t_log_note_like lnl ",
		"WHERE n.author = p.user_id and n.is_delete = 0 and n.status = 1 and n.id = c.note_id and n.id = lnl.note_id and lnl.liker = #{0} ",
		"ORDER BY n.is_hot desc, n.create_time desc",
		"</script>"
		})
	List<NoteQueryDto> myLike(String userId);
	
	/**
	 * @Description: 查询我屏蔽的
	 * @Author cola
	 * @Data: 2020年3月6日
	 * @param userId
	 * @return
	 */
	@Select({"<script>",
		"SELECT n.id, n.title, n.cover, ifnull(n.comment_count, 0) as commentCount, ifnull(n.like_count, 0) as likeCount, n.author, IFNULL(n.collection_count, 0) collectionCount,",
		"ifnull(n.view_count, 0) as viewCount, c.content, ",
		"p.`name`, p.photo, (select case when count(1) > 0 then 1 else 0 end from t_log_note_like where liker = #{0} and note_id = n.id) as isLiked, ",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_note_collection where collector = #{0} and note_id = n.id) as isCollected ",
		"FROM t_note n, t_profile p, t_note_content c, t_note_shield ns ",
		"WHERE n.author = p.user_id and n.is_delete = 0 and n.status = 1 and n.id = c.note_id and n.id = ns.note_id and ns.user_id = #{0} ",
		"ORDER BY n.is_hot desc, n.create_time desc",
		"</script>"
		})
	List<NoteQueryDto> myShield(String userId);
	
	/**
	 * @Description: 修改帖子封面
	 * @Author cola
	 * @Data: 2020年3月3日
	 * @param noteId
	 * @param cover
	 */
	@Update("update t_note set cover = #{1} where id = #{0} ")
	void updateCover(Integer noteId, String cover);

	/**
	 * @Description: 屏蔽帖子
	 * @Author cola
	 * @Data: 2020年3月6日
	 * @param noteId
	 * @param userId
	 */
	@Insert("insert into t_note_shield(note_id, user_id) values (#{0}, #{1}) ")
	void shield(Long noteId, String userId);

	/**
	 * @Description: 取消屏蔽
	 * @Author cola
	 * @Data: 2020年3月7日
	 * @param userId
	 * @param noteId
	 */
	@Delete("delete from t_note_shield where user_id = #{0} and note_id = #{1} ")
	void unshield(String userId, Long noteId);

}
