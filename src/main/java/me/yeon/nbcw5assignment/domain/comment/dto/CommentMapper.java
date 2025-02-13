package me.yeon.nbcw5assignment.domain.comment.dto;

import me.yeon.nbcw5assignment.domain.comment.Comment;

public abstract class CommentMapper {

  public static CommentDto.Req toReq(Comment comment) {
    return CommentDto.Req.builder().content(comment.getContent()).build();
  }

  public static CommentDto.Res toRes(Comment comment) {
    return CommentDto.Res.builder()
        .userEmail(comment.getUser().getEmail())
        .content(comment.getContent())
        .createdAt(comment.getCreatedAt())
        .updatedAt(comment.getUpdatedAt())
        .build();
  }
}
