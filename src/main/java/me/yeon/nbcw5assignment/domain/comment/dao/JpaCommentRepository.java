package me.yeon.nbcw5assignment.domain.comment.dao;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.Comment;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

  private final SpringDataJpaCommentRepository repository;

  @Override
  public Comment save(Comment comment) {
    return repository.save(comment);
  }

  @Override
  public Comment update(Long commentId, String content) {
    Comment comment = repository.findById(commentId).orElseThrow();
    comment.updateComment(content);
    return comment;
  }

  @Override
  public Optional<Comment> findById(Long commentId) {
    return repository.findById(commentId);
  }

  @Override
  public List<Comment> findAllByScheduleId(Long scheduleId) {
    return repository.findAllByScheduleId(scheduleId);
  }

  @Override
  public List<Comment> findAllByUserId(Long userId) {
    return repository.findAllByUserId(userId);
  }

  @Override
  public void delete(Long commentId) {
    repository.deleteById(commentId);
  }
}
