package me.yeon.nbcw5assignment.global.error.detail;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class AbstractErrorDetail {

  private final String defaultMessage;

  public static AbstractErrorDetail of(String defaultMessage) {
    return new AbstractErrorDetail(defaultMessage);
  }
}
