package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.vo.CommentVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;


/**
 * @Description: 评论mapper
 * @Author cola
 * @Date 2020年1月6日
 */
public interface CommentMapper {
	
	/**
	 * @Description: 查询评论列表 apply_id = 0
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param noteCommentQueryVo
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.content , p.`name`, p.photo, n.commenter as userId, IFNULL(n.like_count, 0) likeCount, n.comment_time as time,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_comment_like where liker = #{vo.userId} and comment_id = n.id) as isLiked ",
		"FROM t_comment n, t_profile p WHERE n.commenter = p.user_id and n.note_id = #{vo.noteId} and n.apply_id = 0 and n.status = 0 ",
		" <when test = 'vo.commentId !=  0'> and n.id = #{vo.commentId} </when>",
		" <when test = 'vo.order == \"H\"'> ORDER BY n.like_count desc, n.comment_time desc </when>",
		" <when test = 'vo.order == \"T\"'> ORDER BY n.comment_time desc </when>",
		" <when test = 'vo.order ==  null'> ORDER BY n.like_count desc, n.comment_time desc </when>",
		"</script>"
		})
	List<NoteCommentDto> list(@Param("vo")NoteCommentQueryVo noteCommentQueryVo);
	
	/**
	 * @Description: 查询回复
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param noteCommentQueryVo
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.content, p.photo, p.`name`, n.commenter as userId, IFNULL(n.like_count, 0) likeCount, n.apply_user as applyId,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_comment_like where liker = #{vo.userId} and comment_id = n.id) as isLiked,",
		"(select name from t_profile where user_id = n.apply_user) as applier, n.comment_time as time ",
		"FROM t_comment n, t_profile p WHERE n.commenter = p.user_id and n.apply_id = #{vo.commentId} and n.status = 0 ",
		" <when test='vo.order == \"H\"'> ORDER BY n.like_count desc, n.comment_time asc </when>",
		" <when test='vo.order == \"T\"'> ORDER BY n.comment_time asc </when>",
		" <when test='vo.order ==  null'> ORDER BY n.like_count desc, n.comment_time asc </when>",
		"</script>"
		})
	List<NoteCommentApplyDto> apply(@Param("vo") NoteCommentQueryVo noteCommentQueryVo);
	
	/**
	 * @Description: 评论
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param commentVo
	 * @return
	 */
	@Insert("insert into t_comment ( `apply_id`, `content`, `commenter`, `apply_user`, `comment_time`, `note_id`, `status`) values "
			+ "(#{vo.applyId}, #{vo.content}, #{vo.commenter}, #{vo.applyUser, jdbcType = VARCHAR}, UNIX_TIMESTAMP(NOW()), #{vo.noteId}, 0 )")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "vo.id")
	void save(@Param("vo") CommentVo commentVo);

	/**
	 * @Description: 只查询评论
	 * @Author cola
	 * @Data: 2020年2月26日
	 * @param id
	 * @return
	 */
	@Select({
		"select a.id, a.content, c.name, c.photo, a.commenter, ifnull(a.like_count, 0) likeCount, from_unixtime(a.comment_time, '%m-%d') as time, " + 
		"a.apply_id as applyId, a.apply_user as applyUser, cp.name as applier " + 
		",(select count(*) from t_comment where apply_id = a.id ) as applyCount " +
		"from t_comment a " + 
		"left join t_profile c on c.user_id = a.commenter " + 
		"left join t_profile cp on cp.user_id = a.apply_user " +  // cp被回复者
		"where a.note_id = #{0} and a.apply_id = 0 order by a.comment_time desc "
		})
	List<NoteCommentDto> list1(Long id);

	/**
	 * @Description: 查询所有回复
	 * @Author cola
	 * @Data: 2020年2月26日
	 * @param id
	 * @return
	 */
	@Select({
		"select a.id, a.content, c.name, c.photo, a.commenter, ifnull(a.like_count, 0) likeCount, from_unixtime(a.comment_time, '%m-%d') as time, " + 
		"a.apply_id as applyId, a.apply_user as applyUser, cp.name as applier " + 
		"from t_comment a " + 
		"left join t_profile c on c.user_id = a.commenter " + 
		"left join t_profile cp on cp.user_id = a.apply_user " + 
		"where a.note_id = #{0} and a.apply_id != 0 order by a.comment_time desc "
		})
	List<NoteCommentDto> list2(Long id);
}
