package me.yeon.nbcw5assignment.domain.user.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.User;
import me.yeon.nbcw5assignment.domain.user.dao.UserRepository;
import me.yeon.nbcw5assignment.domain.user.dto.UserDto;
import me.yeon.nbcw5assignment.domain.user.dto.UserMapper;
import me.yeon.nbcw5assignment.global.config.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;

  public UserDto.Res getUserById(Long userId) {
    User fiudUser = repository.findById(userId).orElseThrow();
    return UserMapper.toRes(fiudUser);
  }

  public List<UserDto.Res> getUsers() {
    List<User> findUsers = repository.findAll();
    return findUsers.stream()
        .map(UserMapper::toRes)
        .toList();
  }

  public UserDto.Res saveUser(UserDto.Req req) {
    User user = User.builder()
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword()))
        .build();

    User saveUser = repository.save(user);
    return UserMapper.toRes(saveUser);
  }

  public UserDto.Res updatePassword(Long userId, String password) {
    User updateUser = repository.updatePassword(userId, passwordEncoder.encode(password));
    return UserMapper.toRes(updateUser);
  }

  public void deleteUser(Long userId) {
    repository.delete(userId);
  }
}
