package com.aws.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class BucketDto {
    private String name;
    private Instant creationDate;
}
