package me.yeon.nbcw5assignment.global.error.detail;

import java.util.Locale;
import lombok.Getter;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

@Getter
public class FieldErrorDetail extends AbstractErrorDetail {

  private final String objectName;
  private final String field;
  private final String rejectedValue;

  private FieldErrorDetail(
      String objectName, String field, String rejectedValue, String defaultMessage
  ) {

    super(defaultMessage);
    this.objectName = objectName;
    this.field = field;
    this.rejectedValue = rejectedValue;
  }

  public static FieldErrorDetail of(FieldError fieldError, MessageSource ms, Locale locale) {
    String rejectedValue =
        fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();

    return new FieldErrorDetail(
        fieldError.getObjectName(),
        fieldError.getField(),
        rejectedValue,
        ms.getMessage(fieldError, locale)
    );
  }
}
