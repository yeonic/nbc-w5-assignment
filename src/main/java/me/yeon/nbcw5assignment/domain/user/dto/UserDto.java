package me.yeon.nbcw5assignment.domain.user.dto;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Req {

    private String email;
    private String password;

    @Builder
    public Req(String email, String password) {
      this.email = email;
      this.password = password;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Res {

    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Res(String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
      this.email = email;
      this.createdAt = createdAt;
      this.updatedAt = updatedAt;
    }
  }
}
