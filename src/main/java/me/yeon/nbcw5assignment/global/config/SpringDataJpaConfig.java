package me.yeon.nbcw5assignment.global.config;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.dao.JpaUserRepository;
import me.yeon.nbcw5assignment.domain.user.dao.SpringDataJpaUserRepository;
import me.yeon.nbcw5assignment.domain.user.dao.UserRepository;
import me.yeon.nbcw5assignment.domain.user.service.LoginService;
import me.yeon.nbcw5assignment.domain.user.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SpringDataJpaConfig {

  private final SpringDataJpaUserRepository springDataJpaUserRepository;

  @Bean
  public UserRepository userRepository() {
    return new JpaUserRepository(springDataJpaUserRepository);
  }

  @Bean
  public UserService userService() {
    return new UserService(userRepository());
  }

  @Bean
  public LoginService loginService() {
    return new LoginService(userRepository());
  }
}
