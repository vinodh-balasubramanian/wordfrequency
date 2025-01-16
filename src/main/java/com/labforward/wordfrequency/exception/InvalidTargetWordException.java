package com.labforward.wordfrequency.exception;

/**
 * InvalidTargetWordException for returning custom Exception.
 * <p>
 * This class is a custom Exception to handle invalid Target word.
 * </p>
 */
public class InvalidTargetWordException extends RuntimeException {
  public InvalidTargetWordException(String message) {
    super(message);
  }
}
