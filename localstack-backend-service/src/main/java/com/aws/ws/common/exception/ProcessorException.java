package com.aws.ws.common.exception;


import com.aws.ws.exception.TechnicalMessage;

public class ProcessorException extends TechnicalException {

    public ProcessorException(TechnicalMessage message) {
        super(message);
    }

    public ProcessorException(TechnicalMessage message, Throwable cause) {
        super(message, cause);
    }
}
