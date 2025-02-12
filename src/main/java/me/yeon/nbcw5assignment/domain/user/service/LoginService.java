package me.yeon.nbcw5assignment.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.User;
import me.yeon.nbcw5assignment.domain.user.dao.UserRepository;
import me.yeon.nbcw5assignment.domain.user.dto.UserDto;
import me.yeon.nbcw5assignment.global.config.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public User login(UserDto.Req req) {
    return repository.findByEmail(req.getEmail())
        .filter(user -> passwordEncoder.matches(req.getPassword(), user.getPassword()))
        .orElseThrow();
  }

  public void logout(HttpServletRequest req) {
    HttpSession session = req.getSession(false);
    if (session != null) {
      session.invalidate();
    }
  }
}
