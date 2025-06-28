package com.aws.ws.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DynamoTable {
    private String tableName;
}
