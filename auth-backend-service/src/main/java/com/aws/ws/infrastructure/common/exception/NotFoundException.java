package com.aws.ws.infrastructure.common.exception;

import com.aws.ws.domain.exception.TechnicalMessage;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException {

    private final String code;
    private final String parameter;

    public NotFoundException(TechnicalMessage message, String code, String parameter) {
        super(message.getMessage());
        this.code = code;
        this.parameter = parameter;
    }
}
