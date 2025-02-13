package me.yeon.nbcw5assignment.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Collections;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Response<T> {

  private T data;

  private Paging.Res page;

  private Response(T data) {
    this.data = data;
  }

  private Response(T data, Paging.Res page) {
    this.data = data;
    this.page = page;
  }

  public static <T> Response<List<T>> empty() {
    List<T> emptyList = Collections.emptyList();
    return new Response<>(emptyList);
  }

  public static <T> Response<T> of(T data) {
    return new Response<>(data);
  }

  public static <T> Response<T> of(T data, Paging.Res page) {
    return new Response<>(data, page);
  }
}
