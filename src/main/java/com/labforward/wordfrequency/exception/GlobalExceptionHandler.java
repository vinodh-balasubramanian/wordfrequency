package com.labforward.wordfrequency.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.labforward.wordfrequency.constant.MessageConstants;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Global exception handler for the API to handle and return custom error responses.
 * <p>
 * This class uses `@RestControllerAdvice` to globally handle specific exceptions thrown
 * in the application, and provides a consistent response format.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * Handles the `InvalidTargetWordException` and returns a formatted error response.
   *
   * @param ex      The thrown `InvalidTargetWordException`.
   * @param request The HTTP request that caused the exception.
   * @return A `ResponseEntity` containing the `ApiErrorResponse` with error details.
   */
  @ExceptionHandler(InvalidTargetWordException.class)
  public ResponseEntity<ApiErrorResponse> handleInvalidTargetWordException(
      InvalidTargetWordException ex, HttpServletRequest request) {
    logger.error("Invalid target word exception occurred: {}", ex.getMessage());
    ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), MessageConstants.BAD_REQUEST,
        List.of(ex.getMessage()), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles the `TargetWordTooLongException` and returns a formatted error response.
   *
   * @param ex      The thrown `TargetWordTooLongException`.
   * @param request The HTTP request that caused the exception.
   * @return A `ResponseEntity` containing the `ApiErrorResponse` with error details.
   */
  @ExceptionHandler(TargetWordTooLongException.class)
  public ResponseEntity<ApiErrorResponse> handleTargetWordTooLongException(
      TargetWordTooLongException ex, HttpServletRequest request) {
    logger.error("Target word too long exception occurred: {}", ex.getMessage());
    ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), MessageConstants.BAD_REQUEST,
        List.of(ex.getMessage()), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles validation errors (MethodArgumentNotValidException) and returns a structured error response.
   *
   * @param ex      The thrown `MethodArgumentNotValidException`.
   * @param request The HTTP request that caused the exception.
   * @return A `ResponseEntity` containing the `ApiErrorResponse` with validation error details.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex, HttpServletRequest request) {
    List<String> messages = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .toList();
    logger.error("Validation error occurred: {}", messages);
    ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Error",
        messages, request.getRequestURI());
    return ResponseEntity.badRequest().body(errorResponse);
  }

  /**
   * Handles `IllegalArgumentException` and returns a formatted error response.
   *
   * @param ex      The thrown `IllegalArgumentException`.
   * @param request The HTTP request that caused the exception.
   * @return A `ResponseEntity` containing the `ApiErrorResponse` with error details.
   */
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(
      IllegalArgumentException ex, HttpServletRequest request) {
    logger.error("Illegal argument exception occurred: {}", ex.getMessage());
    ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), MessageConstants.BAD_REQUEST,
        List.of(ex.getMessage()), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  /**
   * Handles generic exceptions and returns an internal server error response.
   *
   * @param ex      The generic `Exception`.
   * @param request The HTTP request that caused the exception.
   * @return A `ResponseEntity` containing the `ApiErrorResponse` with error details.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
    logger.error("Generic exception occurred: {}", ex.getMessage(), ex);
    ApiErrorResponse errorResponse = new ApiErrorResponse(HttpStatus.BAD_REQUEST.value(), MessageConstants.BAD_REQUEST,
        List.of(ex.getMessage()), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
  }
}
