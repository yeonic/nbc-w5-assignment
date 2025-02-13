package me.yeon.nbcw5assignment.global.dto;

import org.springframework.data.domain.Page;

public abstract class PagingMapper {

  public static Paging.Res toRes(Page<?> page, String criteria) {
    return Paging.Res.builder()
        .pageNo(page.getPageable().getPageNumber())
        .pageSize(page.getPageable().getPageSize())
        .sortBy(criteria)
        .totalElement(page.getTotalElements())
        .totalPage(page.getTotalPages())
        .build();
  }
}
