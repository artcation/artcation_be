package com.artcation.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.jspecify.annotations.NonNull;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MdcFilter extends OncePerRequestFilter {

  private static final String REQUEST_ID = "requestId";
  private static final String METHOD = "method";
  private static final String URI = "uri";

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    MDC.put(REQUEST_ID, UUID.randomUUID().toString().substring(0, 8));
    MDC.put(METHOD, request.getMethod());
    MDC.put(URI, request.getRequestURI());

    try {
      filterChain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
