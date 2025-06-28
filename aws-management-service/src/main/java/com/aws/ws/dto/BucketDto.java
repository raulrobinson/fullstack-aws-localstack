package com.aws.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
@Schema(description = "Data Transfer Object for S3 Bucket")
public class BucketDto {

    @Schema(description = "Unique identifier for the bucket", example = "my-bucket-123")
    private String name;

    @Schema(description = "Region where the bucket is located", example = "us-west-2")
    private Instant creationDate;
}
