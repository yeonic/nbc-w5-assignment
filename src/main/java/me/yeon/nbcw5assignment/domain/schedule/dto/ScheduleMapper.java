package me.yeon.nbcw5assignment.domain.schedule.dto;

import me.yeon.nbcw5assignment.domain.schedule.Schedule;

public abstract class ScheduleMapper {

  public static ScheduleDto.Req toReq(Schedule schedule) {
    return ScheduleDto.Req.builder()
        .title(schedule.getTitle())
        .content(schedule.getContent())
        .build();
  }

  public static ScheduleDto.Res toRes(Schedule schedule, Long commentCount) {
    return ScheduleDto.Res.builder()
        .id(schedule.getId())
        .userEmail(schedule.getUser().getEmail())
        .title(schedule.getTitle())
        .commentCount(commentCount)
        .content(schedule.getContent())
        .createdAt(schedule.getCreatedAt())
        .updatedAt(schedule.getUpdatedAt())
        .build();
  }

}
