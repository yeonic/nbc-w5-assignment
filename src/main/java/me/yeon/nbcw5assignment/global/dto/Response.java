package me.yeon.nbcw5assignment.global.dto;

import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {

  private T data;

  private Response(T data) {
    this.data = data;
  }

  public static <T> Response<List<T>> empty() {
    List<T> emptyList = Collections.emptyList();
    return new Response<>(emptyList);

  }

  public static <T> Response<T> of(T data) {
    return new Response<>(data);
  }
}
