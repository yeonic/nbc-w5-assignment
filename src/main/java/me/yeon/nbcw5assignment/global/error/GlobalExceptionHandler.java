package me.yeon.nbcw5assignment.global.error;

import java.util.Locale;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yeon.nbcw5assignment.global.dto.ErrorResponse;
import me.yeon.nbcw5assignment.global.error.detail.AbstractErrorDetail;
import me.yeon.nbcw5assignment.global.error.detail.FieldErrorDetail;
import me.yeon.nbcw5assignment.web.filter.exception.UnauthorizedException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

  private final MessageSource ms;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<FieldErrorDetail> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e,
      Locale locale
  ) {
    log.info("handle MethodArgumentNotValidException", e);
    return ErrorResponse.of(ErrorCode.VALIDATION, e.getBindingResult(), ms, locale);
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorResponse<AbstractErrorDetail> handleUnauthorizedException(UnauthorizedException e) {
    log.info("handle UnauthorizedException", e);
    return ErrorResponse.of(ErrorCode.AUTHORIZAION, e.getMessage());
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<AbstractErrorDetail> handleNoSuchElementException(NoSuchElementException e) {
    log.info("handle NoSuchElementException", e);
    return ErrorResponse.of(ErrorCode.NO_SUCH_ELEMENT, e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse<AbstractErrorDetail> handleException(Exception e) {
    log.info("handle Exception", e);
    return ErrorResponse.of(ErrorCode.EXCEPTION, e.getMessage());
  }
}
