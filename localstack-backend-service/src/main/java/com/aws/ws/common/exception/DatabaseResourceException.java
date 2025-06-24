package com.aws.ws.common.exception;

public class DatabaseResourceException extends RuntimeException {

    public DatabaseResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseResourceException(String message) {
        super(message);
    }
}

