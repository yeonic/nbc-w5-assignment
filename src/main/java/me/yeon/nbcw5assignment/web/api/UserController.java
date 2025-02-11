package me.yeon.nbcw5assignment.web.api;

import java.util.List;
import lombok.RequiredArgsConstructor;
import me.yeon.nbcw5assignment.domain.user.dto.UserDto;
import me.yeon.nbcw5assignment.domain.user.dto.UserDto.Res;
import me.yeon.nbcw5assignment.domain.user.service.UserService;
import me.yeon.nbcw5assignment.global.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

  private final UserService service;

  @GetMapping
  public Response<List<UserDto.Res>> users() {
    List<Res> findUsers = service.getUsers();
    return Response.of(findUsers);
  }

  @GetMapping("/{userId}")
  public Response<UserDto.Res> user(@PathVariable("userId") Long userId) {
    return Response.of(service.getUserById(userId));
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Response<UserDto.Res> addUser(@RequestBody UserDto.Req req) {
    return Response.of(service.saveUser(req));
  }

  @PatchMapping("/{userId}")
  public Response<UserDto.Res> updatePassword(
      @PathVariable("userId") Long userId,
      @RequestBody String password
  ) {
    return Response.of(service.updatePassword(userId, password));
  }

  @PostMapping("/{userId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable("userId") Long userId) {
    service.deleteUser(userId);
  }
}
