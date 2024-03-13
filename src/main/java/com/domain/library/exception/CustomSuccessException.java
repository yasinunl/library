package com.domain.library.exception;

public class CustomSuccessException  extends RuntimeException{

    public CustomSuccessException(String message) {
        super(message);
    }

    public CustomSuccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomSuccessException(Throwable cause) {
        super(cause);
    }

}