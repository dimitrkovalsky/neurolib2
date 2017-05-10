package com.liberty.error;

/**
 * Represents fail of operation executing.
 */
public class OperationFailedException extends ApplicationException {

  public OperationFailedException(String message) {
    super( message);
  }
}
