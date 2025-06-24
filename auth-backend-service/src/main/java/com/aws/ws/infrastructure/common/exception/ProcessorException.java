package com.aws.ws.infrastructure.common.exception;

import com.aws.ws.domain.exception.TechnicalMessage;

public class ProcessorException extends TechnicalException {

    public ProcessorException(TechnicalMessage message) {
        super(message);
    }

    public ProcessorException(TechnicalMessage message, Throwable cause) {
        super(message, cause);
    }
}
