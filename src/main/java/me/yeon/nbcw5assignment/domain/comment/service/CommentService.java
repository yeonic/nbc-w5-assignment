package me.yeon.nbcw5assignment.domain.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.Comment;
import me.yeon.nbcw5assignment.domain.comment.dao.CommentRepository;
import me.yeon.nbcw5assignment.domain.comment.dto.CommentDto;
import me.yeon.nbcw5assignment.domain.comment.dto.CommentMapper;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import me.yeon.nbcw5assignment.domain.schedule.dao.ScheduleRepository;
import me.yeon.nbcw5assignment.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository repository;
  private final ScheduleRepository scheduleRepository;

  public CommentDto.Res addComment(
      Long scheduleId, String commentContent, User author
  ) {
    Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow();
    Comment comment = Comment.builder()
        .content(commentContent)
        .schedule(schedule)
        .user(author)
        .build();
    return CommentMapper.toRes(repository.save(comment));
  }

  public List<CommentDto.Res> getCommentsOfUser(Long userId) {
    List<Comment> allByUserId = repository.findAllByUserId(userId);
    return allByUserId.stream().map(CommentMapper::toRes).toList();
  }

  public List<CommentDto.Res> getCommentsOfSchedule(Long schduleId) {
    List<Comment> allByUserId = repository.findAllByScheduleId(schduleId);
    return allByUserId.stream().map(CommentMapper::toRes).toList();
  }

  public CommentDto.Res updateComment(Long commentId, String comment) {
    Comment findComment = repository.findById(commentId).orElseThrow();
    return CommentMapper.toRes(repository.update(findComment.getId(), comment));
  }

  public void deleteComment(Long commentId) {
    repository.delete(commentId);
  }
}
