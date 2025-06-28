package com.aws.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DynamoTableDto {
    private String tableName;
}
