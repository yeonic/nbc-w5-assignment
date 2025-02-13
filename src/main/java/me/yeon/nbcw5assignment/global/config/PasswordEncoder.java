package me.yeon.nbcw5assignment.global.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.crypto.bcrypt.BCrypt.Result;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

  public String encode(String rawPassword) {
    return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
  }

  public boolean matches(String rawPassword, String encodedPassword) {
    Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
    return result.verified;
  }
}
