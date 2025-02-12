package me.yeon.nbcw5assignment.web.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.User;
import me.yeon.nbcw5assignment.domain.user.dto.UserDto;
import me.yeon.nbcw5assignment.domain.user.dto.UserMapper;
import me.yeon.nbcw5assignment.domain.user.service.LoginService;
import me.yeon.nbcw5assignment.global.dto.Response;
import me.yeon.nbcw5assignment.web.SessionConst;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService service;

  @PostMapping("/login")
  public Response<UserDto.Res> login(
      @Validated @RequestBody UserDto.Req req,
      HttpServletRequest request
  ) {
    User loginUser = service.login(req);

    HttpSession session = request.getSession();
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginUser);

    return Response.of(UserMapper.toRes(loginUser));
  }

  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void logout(HttpServletRequest req) {
    service.logout(req);
  }

}
