package me.yeon.nbcw5assignment.domain.schedule.dao;

import java.util.Optional;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {

  Schedule save(Schedule schedule);

  Schedule update(Long scheduleId, String title, String content);

  Optional<Schedule> findById(Long scheduleId);

  Page<Schedule> findAll(Pageable pageable);

  Page<Schedule> findAllByUserId(Long userId, Pageable pageable);

  void delete(Long scheduleId);
}
