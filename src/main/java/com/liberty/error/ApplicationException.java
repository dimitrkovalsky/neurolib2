package com.liberty.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base Exception name for Error mapping.
 */
@Data
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationException extends RuntimeException {

    private final String message;
    private final String developerMessage;
    private final Exception innerException;


    public ApplicationException(String message) {
        this(message, null);
    }

    public ApplicationException(String message, Exception innerException) {
        this(message, null, innerException);
    }

    public ApplicationException(String message, String developerMessage,
                                Exception innerException) {
        this.message = message;
        this.developerMessage = developerMessage;
        this.innerException = innerException;
    }

}
