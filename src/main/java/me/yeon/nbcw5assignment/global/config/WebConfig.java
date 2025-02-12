package me.yeon.nbcw5assignment.global.config;

import jakarta.servlet.Filter;
import me.yeon.nbcw5assignment.web.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean<Filter> logincheckFilter() {
    return new FilterRegistrationBean<>() {{
      setFilter(new LoginCheckFilter());
      setOrder(1);
      addUrlPatterns("/*");
    }};
  }
}
