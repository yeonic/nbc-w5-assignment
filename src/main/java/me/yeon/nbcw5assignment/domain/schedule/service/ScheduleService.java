package me.yeon.nbcw5assignment.domain.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import me.yeon.nbcw5assignment.domain.schedule.dao.ScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dto.ScheduleDto;
import me.yeon.nbcw5assignment.domain.schedule.dto.ScheduleMapper;
import me.yeon.nbcw5assignment.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

  private final ScheduleRepository repository;

  public ScheduleDto.Res addSchedule(User author, String title, String content) {
    Schedule schedule = Schedule.builder()
        .author(author)
        .title(title)
        .content(content)
        .build();
    return ScheduleMapper.toRes(repository.save(schedule));
  }

  public ScheduleDto.Res updateSchedule(Long scheduleId, String title, String content) {
    Schedule schedule = repository.findById(scheduleId).orElseThrow();

    String updateTitle = title == null ? schedule.getTitle() : title;
    String updateContent = content == null ? schedule.getContent() : content;

    return ScheduleMapper.toRes(repository.update(scheduleId, updateTitle, updateContent));
  }

  public ScheduleDto.Res getSchedule(Long scheduleId) {
    Schedule schedule = repository.findById(scheduleId).orElseThrow();
    return ScheduleMapper.toRes(schedule);
  }

  public List<ScheduleDto.Res> getSchedulesOfUser(Long userId) {
    return repository.findAllByUserId(userId)
        .stream().map(ScheduleMapper::toRes)
        .toList();
  }

  public void deleteSchedule(Long scheduleId) {
    repository.delete(scheduleId);
  }
}
