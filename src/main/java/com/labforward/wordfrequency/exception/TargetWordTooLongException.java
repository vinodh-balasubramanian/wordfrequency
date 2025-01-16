package com.labforward.wordfrequency.exception;

/**
 * TargetWordTooLongException for returning custom Exception.
 * <p>
 * This class is a custom Exception to handle case if Target word is larger than the NoteBook entry.
 * </p>
 */
public class TargetWordTooLongException extends RuntimeException {
  public TargetWordTooLongException(String message) {
    super(message);
  }
}
