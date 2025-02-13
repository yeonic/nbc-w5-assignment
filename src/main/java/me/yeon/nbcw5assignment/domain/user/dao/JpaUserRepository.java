package me.yeon.nbcw5assignment.domain.user.dao;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.User;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepository {

  private final SpringDataJpaUserRepository repository;

  @Override
  public User save(User user) {
    return repository.save(user);
  }

  @Override
  public User updatePassword(Long userId, String password) {
    User findUser = repository.findById(userId).orElseThrow();
    findUser.updatePassword(password);
    return findUser;
  }

  @Override
  public Optional<User> findById(Long userId) {
    return repository.findById(userId);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return repository.findByEmail(email);
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public void delete(Long userId) {
    repository.deleteById(userId);
  }
}
