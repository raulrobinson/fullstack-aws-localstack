package com.aws.ws.domain.exception;

public class InvalidValueException extends BusinessException {
    public InvalidValueException(String parameter, String message) {
        super(message, "INVALID_VALUE", parameter);
    }
}
