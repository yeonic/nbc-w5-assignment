package me.yeon.nbcw5assignment.global.config;

import jakarta.servlet.Filter;
import me.yeon.nbcw5assignment.web.filter.LoginCheckFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Configuration
public class WebConfig {

  private final HandlerExceptionResolver resolver;

  public WebConfig(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
    this.resolver = resolver;
  }

  @Bean
  public FilterRegistrationBean<Filter> loginCheckFilter() {
    return new FilterRegistrationBean<>() {{
      setFilter(new LoginCheckFilter(resolver));
      setOrder(1);
      addUrlPatterns("/*");
    }};
  }
}
