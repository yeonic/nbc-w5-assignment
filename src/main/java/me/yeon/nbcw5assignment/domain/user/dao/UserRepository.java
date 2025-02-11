package me.yeon.nbcw5assignment.domain.user.dao;

import java.util.List;
import java.util.Optional;
import me.yeon.nbcw5assignment.domain.user.User;

public interface UserRepository {

  User save(User user);

  User updatePassword(Long userId, String password);

  Optional<User> findById(Long userId);

  List<User> findAll();

  void delete(Long userId);
}
