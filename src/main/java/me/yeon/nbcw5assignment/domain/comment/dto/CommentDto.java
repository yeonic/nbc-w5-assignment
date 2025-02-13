package me.yeon.nbcw5assignment.domain.comment.dto;

import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CommentDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Req {

    @Size(max = 650)
    private String content;

    @Builder
    public Req(String content) {
      this.content = content;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Res {

    private String userEmail;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Res(String userEmail, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
      this.userEmail = userEmail;
      this.content = content;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
    }
  }
}
