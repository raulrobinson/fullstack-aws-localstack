package com.aws.ws.domain.exception;

import com.aws.ws.infrastructure.common.exception.TechnicalException;

public class NoContentException extends TechnicalException {

    public NoContentException(TechnicalMessage message) {
        super(message);
    }
}
