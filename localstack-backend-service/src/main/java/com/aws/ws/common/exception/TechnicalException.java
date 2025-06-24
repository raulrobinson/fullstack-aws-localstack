package com.aws.ws.common.exception;


import com.aws.ws.exception.TechnicalMessage;

public class TechnicalException extends RuntimeException {

    public TechnicalException(TechnicalMessage message) {
        super(message.getMessage());
    }

    public TechnicalException(TechnicalMessage message, Throwable cause) {
        super(message.getMessage(), cause);
    }
}
