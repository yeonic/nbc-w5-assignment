package me.yeon.nbcw5assignment.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
  // common
  VALIDATION("COMMON_001", "Input validation failed"),
  AUTHORIZAION("COMMON_002", "Unauthorized access"),

  // standard
  NO_SUCH_ELEMENT("STANDARD_001", "Cannot find element"),

  // finally
  EXCEPTION("EXCEPTION", "Uncaught exception");

  private final String code;
  private final String message;
}
