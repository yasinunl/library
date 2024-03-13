package com.domain.library.exception;

public class CustomBadException extends RuntimeException{

    public CustomBadException(String message) {
        super(message);
    }

    public CustomBadException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomBadException(Throwable cause) {
        super(cause);
    }

}
