package com.aws.ws.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class DynamoItem {
    private Map<String, String> attributes;
}
