package com.aws.ws.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LambdaFunctionDto {
    private String functionName;
    private String runtime;
    private String handler;
}
