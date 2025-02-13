package me.yeon.nbcw5assignment.domain.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ScheduleDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Req {

    @NotBlank
    @Size(max = 200)
    private String title;

    @Size(max = 3500)
    private String content;

    @Builder
    public Req(String title, String content) {
      this.title = title;
      this.content = content;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Res {

    private Long id;
    private String userEmail;
    private String title;
    private String content;
    private Long commentCount = 0L;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Res(Long id, String userEmail, String title, String content, Long commentCount,
        LocalDateTime createdAt, LocalDateTime updatedAt) {
      this.id = id;
      this.userEmail = userEmail;
      this.title = title;
      this.content = content;
      this.commentCount = commentCount == null ? 0L : commentCount;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
    }

    public void putCommentCount(Long count) {
      this.commentCount = count;
    }
  }
}
