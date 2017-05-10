package com.liberty.error;

/**
 * Represents validation fail.
 */
public class ValidationException extends ApplicationException {

    public ValidationException(String message) {
        super(message);
    }
}
