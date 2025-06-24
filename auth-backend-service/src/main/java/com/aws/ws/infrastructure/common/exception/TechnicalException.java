package com.aws.ws.infrastructure.common.exception;

import com.aws.ws.domain.exception.TechnicalMessage;

public class TechnicalException extends RuntimeException {

    public TechnicalException(TechnicalMessage message) {
        super(message.getMessage());
    }

    public TechnicalException(TechnicalMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
