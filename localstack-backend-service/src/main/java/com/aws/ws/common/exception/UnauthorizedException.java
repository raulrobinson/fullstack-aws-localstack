package com.aws.ws.common.exception;


import com.aws.ws.exception.TechnicalMessage;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(TechnicalMessage message) {
        super(message.getMessage());
    }
}
