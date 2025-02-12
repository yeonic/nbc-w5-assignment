package me.yeon.nbcw5assignment.global.config;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.dao.CommentRepository;
import me.yeon.nbcw5assignment.domain.comment.dao.JpaCommentRepository;
import me.yeon.nbcw5assignment.domain.comment.dao.SpringDataJpaCommentRepository;
import me.yeon.nbcw5assignment.domain.comment.service.CommentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CommentConfig {

  private final SpringDataJpaCommentRepository springDataJpaCommentRepository;

  @Bean
  public CommentRepository commentRepository() {
    return new JpaCommentRepository(springDataJpaCommentRepository);
  }

  @Bean
  public CommentService commentService() {
    return new CommentService(commentRepository());
  }
}
