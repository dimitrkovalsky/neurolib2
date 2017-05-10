package com.liberty.error;

/**
 * Represent not found error.
 */
public class NotFoundException extends ApplicationException {

  public NotFoundException(String message) {
    super(message);
  }
}
