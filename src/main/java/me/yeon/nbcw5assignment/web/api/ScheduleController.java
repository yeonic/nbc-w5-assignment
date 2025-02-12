package me.yeon.nbcw5assignment.web.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.schedule.dto.ScheduleDto;
import me.yeon.nbcw5assignment.domain.schedule.service.ScheduleService;
import me.yeon.nbcw5assignment.domain.user.User;
import me.yeon.nbcw5assignment.global.dto.Response;
import me.yeon.nbcw5assignment.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {

  private final ScheduleService service;

  @GetMapping
  public Response<List<ScheduleDto.Res>> schedulesOfUser(
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser
  ) {
    return Response.of(service.getSchedulesOfUser(currentUser.getId()));
  }

  @GetMapping("/{scheduleId}")
  public Response<ScheduleDto.Res> schedule(@PathVariable(name = "scheduleId") Long scheduleId) {
    return Response.of(service.getSchedule(scheduleId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Response<ScheduleDto.Res> addSchedule(
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser,
      @RequestBody ScheduleDto.Req req
  ) {
    return Response.of(service.addSchedule(currentUser, req.getTitle(), req.getContent()));
  }

  @PatchMapping("/{scheduleId}")
  public Response<ScheduleDto.Res> update(
      @PathVariable(name = "scheduleId") Long scheduleId,
      @RequestBody ScheduleDto.Req req,
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser
  ) {
    return Response.of(service.updateSchedule(scheduleId, req.getTitle(), req.getContent()));
  }

  @DeleteMapping("/{scheduleId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
      @PathVariable(name = "scheduleId") Long scheduleId,
      @SessionAttribute(name = SessionConst.LOGIN_MEMBER) User currentUser
  ) {
    service.deleteSchedule(scheduleId);
  }
}
