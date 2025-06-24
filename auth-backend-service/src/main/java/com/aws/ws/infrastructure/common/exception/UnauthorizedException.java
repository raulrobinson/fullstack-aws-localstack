package com.aws.ws.infrastructure.common.exception;

import com.aws.ws.domain.exception.TechnicalMessage;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(TechnicalMessage message) {
        super(message.getMessage());
    }
}
