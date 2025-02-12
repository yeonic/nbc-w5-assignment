package me.yeon.nbcw5assignment.domain.schedule.dto;

import me.yeon.nbcw5assignment.domain.schedule.Schedule;

public abstract class ScheduleMapper {

  public static ScheduleDto.Req toReq(Schedule schedule) {
    return ScheduleDto.Req.builder()
        .title(schedule.getTitle())
        .content(schedule.getContent())
        .build();
  }

  public static ScheduleDto.Res toRes(Schedule schedule) {
    return ScheduleDto.Res.builder()
        .userEmail(schedule.getUser().getEmail())
        .title(schedule.getTitle())
        .content(schedule.getContent())
        .createdAt(schedule.getCreatedAt())
        .updatedAt(schedule.getUpdatedAt())
        .build();
  }
}
