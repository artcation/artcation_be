package com.artcation.global.response;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(NON_NULL)
public record ApiResponse<T>(String code, String message, T data) {

  public static ApiResponse<Void> success() {
    return new ApiResponse<>(null, "success", null);
  }

  public static <T> ApiResponse<T> success(T data) {
    return new ApiResponse<>(null, "success", data);
  }

  public static ApiResponse<Void> fail(String code, String message) {
    return new ApiResponse<>(code, message, null);
  }
}
