package me.yeon.nbcw5assignment.domain.user.dao;

import java.util.Optional;
import me.yeon.nbcw5assignment.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}
