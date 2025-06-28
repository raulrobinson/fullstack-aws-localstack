package com.aws.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Data Transfer Object for SQS Queue")
public class SqsQueueDto {

    @Schema(description = "The URL of the SQS queue")
    private String queueUrl;
}
