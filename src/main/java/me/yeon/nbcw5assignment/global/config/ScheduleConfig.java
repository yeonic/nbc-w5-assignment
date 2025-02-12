package me.yeon.nbcw5assignment.global.config;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.schedule.dao.JpaScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dao.ScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dao.SpringDataJpaScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.service.ScheduleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ScheduleConfig {

  private final SpringDataJpaScheduleRepository springDataJpaScheduleRepository;

  @Bean
  public ScheduleRepository scheduleRepository() {
    return new JpaScheduleRepository(springDataJpaScheduleRepository);
  }

  @Bean
  public ScheduleService scheduleService() {
    return new ScheduleService(scheduleRepository());
  }
}
