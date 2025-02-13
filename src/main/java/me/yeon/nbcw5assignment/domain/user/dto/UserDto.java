package me.yeon.nbcw5assignment.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserDto {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Req {

    @NotBlank
    @Email(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private String email;

    @NotBlank
    @Size(max = 80)
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
