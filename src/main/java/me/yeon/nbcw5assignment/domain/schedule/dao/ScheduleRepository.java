package me.yeon.nbcw5assignment.domain.schedule.dao;

import java.util.List;
import java.util.Optional;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;

public interface ScheduleRepository {

  Schedule save(Schedule schedule);

  Schedule update(Long scheduleId, String title, String content);

  Optional<Schedule> findById(Long scheduleId);

  List<Schedule> findAllByUserId(Long userId);

  void delete(Long scheduleId);
}
