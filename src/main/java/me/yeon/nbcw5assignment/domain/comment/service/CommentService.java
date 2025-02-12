package me.yeon.nbcw5assignment.domain.comment.service;

import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.comment.dao.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository repository;
}
