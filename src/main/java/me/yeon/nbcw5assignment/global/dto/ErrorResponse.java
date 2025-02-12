package me.yeon.nbcw5assignment.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.yeon.nbcw5assignment.global.error.ErrorCode;
import me.yeon.nbcw5assignment.global.error.detail.AbstractErrorDetail;
import me.yeon.nbcw5assignment.global.error.detail.FieldErrorDetail;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse<T extends AbstractErrorDetail> {

  private String errorCode;
  private String message;
  private List<T> errors;

  private ErrorResponse(ErrorCode errorCode) {
    this.errorCode = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  private ErrorResponse(
      ErrorCode errorCode, List<FieldError> errors, MessageSource ms,
      Locale locale, Class<T> aClass
  ) {
    this(errorCode);

    if (!aClass.equals(FieldErrorDetail.class)) {
      throw new IllegalStateException("잘못된 생성자를 사용했습니다.");
    }
    this.errors = errors.stream()
        .map(error -> aClass.cast(FieldErrorDetail.of(error, ms, locale)))
        .toList();
  }

  private ErrorResponse(ErrorCode errorCode, String defaultErrorMessage, Class<T> aClass) {
    this(errorCode);

    if (!aClass.equals(AbstractErrorDetail.class)) {
      throw new IllegalStateException("잘못된 생성자를 사용했습니다.");
    }
    this.errors = List.of(aClass.cast(AbstractErrorDetail.of(defaultErrorMessage)));
  }

  public static ErrorResponse<FieldErrorDetail> of(
      ErrorCode errorCode, BindingResult bindingResult, MessageSource ms, Locale locale
  ) {
    return new ErrorResponse<>(errorCode, bindingResult.getFieldErrors(), ms, locale,
        FieldErrorDetail.class);
  }

  public static ErrorResponse<AbstractErrorDetail> of(ErrorCode errorCode, String errorMessage) {
    return new ErrorResponse<>(errorCode, errorMessage, AbstractErrorDetail.class);
  }
}
