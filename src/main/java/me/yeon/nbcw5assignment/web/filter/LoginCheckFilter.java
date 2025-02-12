package me.yeon.nbcw5assignment.web.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.yeon.nbcw5assignment.web.SessionConst;
import me.yeon.nbcw5assignment.web.filter.exception.UnauthorizedException;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Slf4j
@RequiredArgsConstructor
public class LoginCheckFilter implements Filter {

  private final HandlerExceptionResolver resolver;
  private static final String[] whitelist = {"/api/login", "/api/logout", "/api/users/signup"};

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

  private boolean isLoginCheckPath(String requestURI) {
    return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
  }

  private boolean isUnauthorized(HttpSession session) {
    return session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null;
  }
}
