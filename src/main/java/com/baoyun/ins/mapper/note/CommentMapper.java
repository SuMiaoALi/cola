package com.baoyun.ins.mapper.note;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baoyun.ins.entity.note.dto.NoteCommentApplyDto;
import com.baoyun.ins.entity.note.dto.NoteCommentDto;
import com.baoyun.ins.entity.note.dto.NoteOperateDto;
import com.baoyun.ins.entity.note.vo.CommentVo;
import com.baoyun.ins.entity.note.vo.NoteCommentQueryVo;


/**
 * @Description: 评论mapper
 * @Author cola
 * @Date 2020年1月6日
 */
public interface CommentMapper {
	
	/**
	 * @Description: 查询评论列表 apply_id = null
	 * @Author cola
	 * @Data: 2020年1月6日
	 * @param noteCommentQueryVo
	 * @return
	 */
	@Select({
		"<script>",
		"SELECT n.id, n.content , p.`name`, p.photo, n.commenter as userId, IFNULL(n.like_count, 0) likeCount, n.comment_time as time,",
		"(select case when count(1) > 0 then 1 else 0 end from t_log_comment_like where liker = #{vo.userId} and comment_id = n.id) as isLiked ",
		"FROM t_comment n, t_profile p WHERE n.commenter = p.user_id and n.note_id = #{vo.noteId} and n.apply_id is null and n.status = 0 ",
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
	@Select("insert into t_comment ( `apply_id`, `content`, `commenter`, `apply_user`, `comment_time`, `note_id`,status) values "
			+ "(#{applyId}, #{content}, #{commenter}, #{applayer,jdbcType=VARCHAR}, UNIX_TIMESTAMP(NOW()), #{noteId}, 0 )")
	NoteOperateDto save(CommentVo commentVo);
}
