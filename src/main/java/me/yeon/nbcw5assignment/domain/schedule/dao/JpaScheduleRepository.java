package me.yeon.nbcw5assignment.domain.schedule.dao;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  public Page<Schedule> findAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Page<Schedule> findAllByUserId(Long userId, Pageable pageable) {
    return repository.findAllByUserId(userId, pageable);
  }

  @Override
  public void delete(Long scheduleId) {
    repository.deleteById(scheduleId);
  }
}
