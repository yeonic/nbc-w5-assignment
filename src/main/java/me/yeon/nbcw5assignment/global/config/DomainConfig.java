package me.yeon.nbcw5assignment.global.config;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.dao.CommentRepository;
import me.yeon.nbcw5assignment.domain.comment.dao.JpaCommentRepository;
import me.yeon.nbcw5assignment.domain.comment.dao.SpringDataJpaCommentRepository;
import me.yeon.nbcw5assignment.domain.comment.service.CommentService;
import me.yeon.nbcw5assignment.domain.schedule.dao.JpaScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dao.ScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.dao.SpringDataJpaScheduleRepository;
import me.yeon.nbcw5assignment.domain.schedule.service.ScheduleService;
import me.yeon.nbcw5assignment.domain.user.dao.JpaUserRepository;
import me.yeon.nbcw5assignment.domain.user.dao.SpringDataJpaUserRepository;
import me.yeon.nbcw5assignment.domain.user.dao.UserRepository;
import me.yeon.nbcw5assignment.domain.user.service.LoginService;
import me.yeon.nbcw5assignment.domain.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DomainConfig {

  private final SpringDataJpaUserRepository springDataJpaUserRepository;
  private final PasswordEncoder passwordEncoder;

  private final SpringDataJpaScheduleRepository springDataJpaScheduleRepository;

  private final SpringDataJpaCommentRepository springDataJpaCommentRepository;

  // UserConfig
  @Bean
  public UserRepository userRepository() {
    return new JpaUserRepository(springDataJpaUserRepository);
  }

  @Bean
  public UserService userService() {
    return new UserService(userRepository(), passwordEncoder);
  }

  @Bean
  public LoginService loginService() {
    return new LoginService(userRepository(), passwordEncoder);
  }

  // ScheduleConfig
  @Bean
  public ScheduleRepository scheduleRepository() {
    return new JpaScheduleRepository(springDataJpaScheduleRepository);
  }

  @Bean
  public ScheduleService scheduleService() {
    return new ScheduleService(scheduleRepository(), commentRepository());
  }

  // CommentConfig
  @Bean
  public CommentRepository commentRepository() {
    return new JpaCommentRepository(springDataJpaCommentRepository);
  }

  @Bean
  public CommentService commentService() {
    return new CommentService(commentRepository(), scheduleRepository());
  }
}
