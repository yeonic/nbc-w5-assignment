package me.yeon.nbcw5assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class NbcW5AssignmentApplication {

  public static void main(String[] args) {
    SpringApplication.run(NbcW5AssignmentApplication.class, args);
  }

}
