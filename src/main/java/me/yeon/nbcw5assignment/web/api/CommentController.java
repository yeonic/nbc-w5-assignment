package me.yeon.nbcw5assignment.web.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.dto.CommentDto;
import me.yeon.nbcw5assignment.domain.comment.service.CommentService;
import me.yeon.nbcw5assignment.domain.user.User;
import me.yeon.nbcw5assignment.global.dto.Response;
import me.yeon.nbcw5assignment.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/schedules/{scheduleId}/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService service;

  @GetMapping
  public Response<List<CommentDto.Res>> commentsOf(
      @PathVariable(name = "scheduleId") Long scheduleId
  ) {
    return Response.of(service.getCommentsOfSchedule(scheduleId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Response<CommentDto.Res> addComment(
      @PathVariable(name = "scheduleId") Long scheduleId,
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser,
      @RequestBody CommentDto.Res req
  ) {
    return Response.of(service.addComment(scheduleId, req.getContent(), currentUser));
  }

  @PatchMapping("/{commentId}")
  public Response<CommentDto.Res> update(
      @PathVariable(name = "commentId") Long commentId,
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser,
      @RequestBody CommentDto.Res req) {
    return Response.of(service.updateComment(commentId, req.getContent()));
  }

  @DeleteMapping("/{commentId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable(name = "commentId") Long commentId) {
    service.deleteComment(commentId);
  }

}
