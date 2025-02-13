package me.yeon.nbcw5assignment.domain.user.dto;

import me.yeon.nbcw5assignment.domain.user.User;

public abstract class UserMapper {

  public static UserDto.Req toReq(User user) {
    return UserDto.Req.builder()
        .email(user.getEmail())
        .password(user.getPassword())
        .build();
  }

  public static UserDto.Res toRes(User user) {
    return UserDto.Res.builder()
        .email(user.getEmail())
        .createdAt(user.getCreatedAt())
        .updatedAt(user.getUpdatedAt())
        .build();
  }
}
