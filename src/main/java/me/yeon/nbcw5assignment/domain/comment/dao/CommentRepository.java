package me.yeon.nbcw5assignment.domain.comment.dao;

import java.util.List;
import me.yeon.nbcw5assignment.domain.comment.Comment;

public interface CommentRepository {

  Comment save(Comment comment);

  Comment update(Long commentId, String content);

  List<Comment> findAllByScheduleId(Long scheduleId);

  List<Comment> findAllByUserId(Long userId);

  void delete(Long commentId);
}
