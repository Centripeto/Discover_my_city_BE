package com.boomers.www.discover_my_city.config;

import com.boomers.www.discover_my_city.core.handler.AuthFacade;
import com.boomers.www.discover_my_city.core.model.user.User;
import com.boomers.www.discover_my_city.service.UserSecurity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthFacade authFacade;

  @Autowired
  public JwtAuthenticationFilter(AuthFacade authFacade) {
    this.authFacade = authFacade;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    if (request.getServletPath().contains("/api/auth")) {
      filterChain.doFilter(request, response);
      return;
    }
    final String authHeader = request.getHeader("Authorization");
    final String jwt;
    final String username;
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }
    jwt = authHeader.substring(7);
    User user = authFacade.extractUserFromToken(jwt).orElse(null);
    if (user != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      if (authFacade.isTokenValid(jwt)) {
        UserSecurity userSecurity =
            new UserSecurity(
                user.getPassword(), user.getUsername(), List.of(user.getRole().toString()));
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userSecurity, null, userSecurity.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }
    filterChain.doFilter(request, response);
  }
}
