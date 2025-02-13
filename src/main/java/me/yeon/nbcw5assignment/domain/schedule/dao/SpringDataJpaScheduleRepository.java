package me.yeon.nbcw5assignment.domain.schedule.dao;

import me.yeon.nbcw5assignment.domain.schedule.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaScheduleRepository extends JpaRepository<Schedule, Long> {

  Page<Schedule> findAll(Pageable pageable);

  Page<Schedule> findAllByUserId(Long userId, Pageable pageable);

}
