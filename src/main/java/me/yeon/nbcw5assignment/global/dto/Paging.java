package me.yeon.nbcw5assignment.global.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Paging {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Req {

    private int pageNo;
    private int pageSize;
    private String sortBy;

    @Builder
    public Req(int pageNo, int pageSize, String sortBy) {
      this.pageNo = pageNo;
      this.pageSize = pageSize;
      this.sortBy = sortBy;
    }
  }

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Res {

    private int pageNo;
    private int pageSize;
    private String sortBy;
    private long totalElement;
    private int totalPage;

    @Builder
    public Res(int pageNo, int pageSize, String sortBy, long totalElement, int totalPage) {
      this.pageNo = pageNo;
      this.pageSize = pageSize;
      this.sortBy = sortBy;
      this.totalElement = totalElement;
      this.totalPage = totalPage;
    }
  }
}
