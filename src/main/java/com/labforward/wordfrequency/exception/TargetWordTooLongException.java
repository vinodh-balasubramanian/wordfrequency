package com.labforward.wordfrequency.exception;

public class TargetWordTooLongException extends RuntimeException{
  public TargetWordTooLongException(String message) {
    super(message);
  }
}
