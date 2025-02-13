# Week5 일정 관리 앱 서버 구현 과제

## About Project

| 구분     | 내용                                                   |
|--------|------------------------------------------------------|
| 목적     | Spring Data JPA를 통해 DB에 데이터를 저장하는 Web API를 구현할 수 있다. |
| 기간     | 2025.2.11 ~ 2025.2.13                                |
| 설명     | User, Schedule, Comment로 구성된 일정 관리 앱의 백엔드 구성.        |
| 사용 기술  | Java, SpringMVC, JPA, SpringDataJpa                  |
| JDK    | Amazon Corretto 17.0.13 - aarch64                    |
| Spring | Boot 3.4.2, Core 6.2.2                               |

<br/><br/>

## ERD & API 명세

### ERD

<img src="https://raw.githubusercontent.com/yeonic/nbc-w5-assignment/c782442595a2b197a9a953baee824dd5bc558385/resources/0_erd.png" alt="erd">

<br/>

### API 명세

- User

| URI                 | HTTP   | 설명                                            |
|---------------------|--------|-----------------------------------------------|
| /api/users          | GET    | 유저들의 목록을 조회합니다.                               |
| /api/users/{userId} | GET    | userId에 해당하는 유저를 조회합니다.                       |
| /api/users/signup   | POST   | RequestBody로 email, password를 입력받아 유저를 생성합니다. |
| /api/users/{userId} | PATCH  | RequestBody로 password를 입력받아 비밀번호를 변경합니다.      |                                               
| /api/users/{userId} | DELETE | userId에 해당하는 유저를 삭제합니다.                       |

<br/>

- Login

| URI         | HTTP | 설명                                         |
|-------------|------|--------------------------------------------|
| /api/login  | POST | RequestBody로 email, password를 입력받아 로그인합니다. |
| /api/logout | POST | 현재 로그인된 유저를 로그아웃합니다.                       |

<br/>

- Schedule

| URI                         | HTTP   | 설명                                                                      |
|-----------------------------|--------|-------------------------------------------------------------------------|
| /api/schedules              | GET    | schedule 목록을 조회합니다. 로그인된 사용자의 경우, 자신이 생성한 일정을, 아닌 사용자의 경우 모든 일정을 조회합니다. |
| /api/schedules              | POST   | RequestBody로 일정 이름, 할 일을 입력받아 일정을 생성합니다.                                |
| /api/schedules/{scheduleId} | GET    | scheduleId에 해당하는 일정 하나를 조회합니다.                                          |
| /api/schedules/{scheduleId} | PATCH  | RequestBody로 일정 이름, 할 일을 입력받아 일정을 수정합니다.                                |
| /api/schedules/{scheduleId} | DELETE | scheduleId에 해당하는 일정 하나를 삭제합니다.                                          |

<br/>

- Comment

| URI                                              | HTTP   | 설명                                            |
|--------------------------------------------------|--------|-----------------------------------------------|
| /api/schedules/{scheduleId}/comments             | GET    | schedule에 등록된 댓글을 모두 조회합니다.                   |
| /api/schedules/{scheduleId}/comments             | POST   | RequestBody로 댓글 내용을 입력받아 schedule에 댓글을 등록합니다. |
| /api/schedules/{scheduleId}/comments/{commentId} | PATCH  | RequestBody로 댓글 내용을 입력받아 댓글을 수정합니다.           |
| /api/schedules/{scheduleId}/comments/{commentId} | DELETE | commentId에 해당하는 댓글 하나를 삭제합니다.                 |

<br/><br/>

## Implementation Detail

### Authorization Filter

- LoginCheckFilter의 doFilter에서 `HttpServletRequest`를 통해 조회한 세션 검증
- ControllerAdvice에서 에러를 처리하기 위해, `HandlerExceptionResolver`에 에러처리 위임

```java

@Override
public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
    FilterChain filterChain) throws IOException, ServletException {

  HttpServletRequest request = (HttpServletRequest) servletRequest;
  HttpServletResponse response = (HttpServletResponse) servletResponse;
  String requestURI = request.getRequestURI();

  if (isLoginCheckPath(requestURI)) {
    HttpSession session = request.getSession(false);
    if (isUnauthorized(session)) {
      try {
        throw new UnauthorizedException("인증되지 않은 사용자입니다.");
      } catch (Exception e) {
        resolver.resolveException(request, response, null, e);
        return;
      }
    }
  }

  filterChain.doFilter(servletRequest, servletResponse);
}
```

<br/>

### Pagination

- JpaRepository에 Paging용 메서드 선언

```java
public interface SpringDataJpaScheduleRepository extends JpaRepository<Schedule, Long> {

  Page<Schedule> findAll(Pageable pageable);

  Page<Schedule> findAllByUserId(Long userId, Pageable pageable);

}
```

<br/>

- Service에서 PageRequest 활용해서 페이지 요청

```java
public Page<ScheduleDto.Res> getSchedulesOfUser(Long userId, int pageNo, int pageSize,
    String creteria) {
  // 페이지 번호, 사이즈, 정렬 기준을 통해 PageRequest 생성
  Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(Direction.DESC, creteria));

  // repository에서 Pageable 객체를 활용해 페이지 조회
  return repository.findAllByUserId(userId, pageable).map(schedule -> {
    Long commentCount = commentRepository.countAllByScheduleId(schedule.getId());
    return ScheduleMapper.toRes(schedule, commentCount);
  });
}
```

<br/>

- Paging 객체로 페이지 정보 가공 후 Response에 탑재

```java
public class Paging {

  // ...

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  public static class Res {

    private int pageNo;
    private int pageSize;
    private String sortBy;
    private long totalElement;
    private int totalPage;

    @Builder
    public Res(int pageNo, int pageSize, String sortBy, long totalElement, int totalPage) {
      this.pageNo = pageNo;
      this.pageSize = pageSize;
      this.sortBy = sortBy;
      this.totalElement = totalElement;
      this.totalPage = totalPage;
    }
  }
}
```

<br/>

### Validation / Exception Handling

- @Validated를 통해 validation 적용
- 에러 타입별 handle, 처리되지 않은 에러는 Exception handler로 처리
- 에러 Http Status 명시

```java
public class GlobalExceptionHandler {

  private final MessageSource ms;


  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse<FieldErrorDetail> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e,
      Locale locale
  ) {
    log.info("handle MethodArgumentNotValidException", e);
    return ErrorResponse.of(ErrorCode.VALIDATION, e.getBindingResult(), ms, locale);
  }

  // ...
  // 기본 Exception handler 포함
}
```

<br/>

- 에러 메시지 공통 처리를 위한 ErrorResponse 구성

```properties
# errors.properties
Size.req.title=제목은 60자 이내여야 합니다.
Size.req.password=잘못된 비밀번호입니다.
Size.java.lang.String=입력 길이가 초과되었습니다.
NotBlank.java.lang.String=필수 문자입니다.
Email.java.lang.String=잘못된 이메일입니다.
```

<br/>

- Error Response는 에러 타입에 따라 형태가 달라짐
- Validation error일 경우, field와 rejectedValue를 포함한 에러 메시지
- 그 외의 경우, 기본 데이터에 에러 메시지를 추가한 형태가 됨

```java

@Getter
@JsonInclude(Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse<T extends AbstractErrorDetail> {

  private String errorCode;
  private String message;
  private List<T> errors;

  private ErrorResponse(ErrorCode errorCode) {
    this.errorCode = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  private ErrorResponse(
      ErrorCode errorCode, List<FieldError> errors, MessageSource ms,
      Locale locale, Class<T> aClass
  ) {
    this(errorCode);

    if (!aClass.equals(FieldErrorDetail.class)) {
      throw new IllegalStateException("잘못된 생성자를 사용했습니다.");
    }
    this.errors = errors.stream()
        .map(error -> aClass.cast(FieldErrorDetail.of(error, ms, locale)))
        .toList();
  }

  private ErrorResponse(ErrorCode errorCode, String defaultErrorMessage, Class<T> aClass) {
    this(errorCode);

    if (!aClass.equals(AbstractErrorDetail.class)) {
      throw new IllegalStateException("잘못된 생성자를 사용했습니다.");
    }
    this.errors = List.of(aClass.cast(AbstractErrorDetail.of(defaultErrorMessage)));
  }

  public static ErrorResponse<FieldErrorDetail> of(
      ErrorCode errorCode, BindingResult bindingResult, MessageSource ms, Locale locale
  ) {
    return new ErrorResponse<>(errorCode, bindingResult.getFieldErrors(), ms, locale,
        FieldErrorDetail.class);
  }

  public static ErrorResponse<AbstractErrorDetail> of(ErrorCode errorCode, String errorMessage) {
    return new ErrorResponse<>(errorCode, errorMessage, AbstractErrorDetail.class);
  }
}
```

<br/>

- Field 에러의 형태를 정의한 FieldErrorDetail은 다음과 같음
- static function인 of에 message를 담을 때, messageSource에서 값을 꺼내 담음

```java
public class FieldErrorDetail extends AbstractErrorDetail {

  private final String objectName;
  private final String field;
  private final String rejectedValue;

  private FieldErrorDetail(
      String objectName, String field, String rejectedValue, String defaultMessage
  ) {

    super(defaultMessage);
    this.objectName = objectName;
    this.field = field;
    this.rejectedValue = rejectedValue;
  }

  public static FieldErrorDetail of(FieldError fieldError, MessageSource ms, Locale locale) {
    String rejectedValue =
        fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString();

    return new FieldErrorDetail(
        fieldError.getObjectName(),
        fieldError.getField(),
        rejectedValue,
        ms.getMessage(fieldError, locale) // 메시지 공통처리를 위함
    );
  }
}
```

<br/>

### Password Encoder

- BCrypt library를 이용한 모듈 직접 구성
- 메서드 구성은 Spring Security의 `BCryptPasswordEncoder`와 같음

```java

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

```

<br/>

- Login 시에 암호화된 password 지원

```java

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

  // ...
  public User login(UserDto.Req req) {
    return repository.findByEmail(req.getEmail())
        .filter(user -> passwordEncoder.matches(req.getPassword(),
            user.getPassword())) // passwordEncoder를 활용한 암호화
        .orElseThrow();
  }
  // ...
}

```

<br/>

- User 생성 시에 암호화된 password 지원

```java

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

  //...
  public UserDto.Res saveUser(UserDto.Req req) {
    User user = User.builder()
        .email(req.getEmail())
        .password(passwordEncoder.encode(req.getPassword())) // passwordEncoder 활용
        .build();

    User saveUser = repository.save(user);
    return UserMapper.toRes(saveUser);
  }
  // ...
}
```