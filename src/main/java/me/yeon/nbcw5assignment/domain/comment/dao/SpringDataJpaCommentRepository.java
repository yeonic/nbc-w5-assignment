package me.yeon.nbcw5assignment.domain.comment.dao;

import java.util.List;
import me.yeon.nbcw5assignment.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaCommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByScheduleId(Long scheduleId);

  Long countAllByScheduleId(Long scheduleId);

  List<Comment> findAllByUserId(Long userId);

}
