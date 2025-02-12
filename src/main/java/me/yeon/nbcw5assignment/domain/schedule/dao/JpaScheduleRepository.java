package me.yeon.nbcw5assignment.domain.schedule.dao;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaScheduleRepository implements ScheduleRepository {

  private final SpringDataJpaScheduleRepository repository;

  @Override
  public Schedule save(Schedule schedule) {
    return repository.save(schedule);
  }

  @Override
  public Schedule update(Long scheduleId, String title, String content) {
    Schedule schedule = repository.findById(scheduleId).orElseThrow();
    schedule.updateSchedule(title, content);
    return schedule;
  }

  @Override
  public Optional<Schedule> findById(Long scheduleId) {
    return repository.findById(scheduleId);
  }

  @Override
  public List<Schedule> findAllByUserId(Long userId) {
    return repository.findAllByUserId(userId);
  }

  @Override
  public void delete(Long scheduleId) {
    repository.deleteById(scheduleId);
  }
}
