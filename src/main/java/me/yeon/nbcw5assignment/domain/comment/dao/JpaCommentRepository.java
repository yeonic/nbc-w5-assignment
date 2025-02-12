package me.yeon.nbcw5assignment.domain.comment.dao;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.Comment;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

  private final SpringDataJpaCommentRepository repository;

  @Override
  public Comment save(Comment comment) {
    return null;
  }

  @Override
  public Comment update(Long commentId, String content) {
    return null;
  }

  @Override
  public List<Comment> findAllByScheduleId(Long scheduleId) {
    return List.of();
  }

  @Override
  public List<Comment> findAllByUserId(Long userId) {
    return List.of();
  }

  @Override
  public void delete(Long commentId) {

  }
}
