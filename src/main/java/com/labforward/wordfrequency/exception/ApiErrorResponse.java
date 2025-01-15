package com.labforward.wordfrequency.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error,
    List<String> messages,
    String path
) {
  public ApiErrorResponse(int status, String error, List<String> messages, String path) {
    this(LocalDateTime.now(), status, error, messages, path);
  }
}
