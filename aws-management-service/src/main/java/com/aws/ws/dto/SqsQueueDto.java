package com.aws.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SqsQueueDto {
    private String queueUrl;
}
