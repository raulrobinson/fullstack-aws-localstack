package com.aws.ws.exception;

import com.aws.ws.common.exception.TechnicalException;

public class NoContentException extends TechnicalException {

    public NoContentException(TechnicalMessage message) {
        super(message);
    }
}
