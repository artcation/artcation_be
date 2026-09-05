package com.artcation.global.exception;

import com.artcation.global.response.ApiResponse;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  // 비즈니스 예외
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
    ErrorCode code = e.getErrorCode();
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), code.getMessage());
    return toResponse(code);
  }

  // @Valid 검증 실패
  @ExceptionHandler(BindException.class)
  public ResponseEntity<ApiResponse<Void>> handleValidation(BindException e) {
    String message =
        e.getBindingResult().getFieldErrors().stream()
            .map(err -> err.getField() + ": " + err.getDefaultMessage())
            .collect(Collectors.joining(", "));

    ErrorCode code = GlobalErrorCode.INVALID_INPUT;
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), message);
    return toResponse(code, message);
  }

  // 요청 파싱/바인딩/파라미터 검증 실패
  @ExceptionHandler({
    HandlerMethodValidationException.class,
    HttpMessageNotReadableException.class,
    ServletRequestBindingException.class,
    TypeMismatchException.class
  })
  public ResponseEntity<ApiResponse<Void>> handleBadRequest(Exception e) {
    ErrorCode code = GlobalErrorCode.INVALID_INPUT;
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), e.getMessage());
    return toResponse(code);
  }

  // 404
  @ExceptionHandler(NoResourceFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNotFound(NoResourceFoundException e) {
    ErrorCode code = GlobalErrorCode.NOT_FOUND;
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), e.getMessage());
    return toResponse(code);
  }

  // 405
  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(
      HttpRequestMethodNotSupportedException e) {
    ErrorCode code = GlobalErrorCode.METHOD_NOT_ALLOWED;
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), e.getMessage());
    return toResponse(code);
  }

  // 415
  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException e) {
    ErrorCode code = GlobalErrorCode.UNSUPPORTED_MEDIA_TYPE;
    log.warn("[{}] {} - {}", code.getStatus().value(), code.name(), e.getMessage());
    return toResponse(code);
  }

  // 500
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleUnexpected(Exception e) {
    ErrorCode code = GlobalErrorCode.INTERNAL_ERROR;
    log.error("[{}] {} - {}", code.getStatus().value(), code.name(), e.getMessage(), e);
    return toResponse(code);
  }

  private ResponseEntity<ApiResponse<Void>> toResponse(ErrorCode code) {
    return toResponse(code, code.getMessage());
  }

  private ResponseEntity<ApiResponse<Void>> toResponse(ErrorCode code, String message) {
    return ResponseEntity.status(code.getStatus()).body(ApiResponse.fail(code.name(), message));
  }
}
