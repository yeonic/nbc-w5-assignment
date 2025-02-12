package me.yeon.nbcw5assignment.domain.schedule.dao;

import java.util.List;
import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaScheduleRepository extends JpaRepository<Schedule, Long> {
  
  List<Schedule> findAllByUserId(Long id);

}
