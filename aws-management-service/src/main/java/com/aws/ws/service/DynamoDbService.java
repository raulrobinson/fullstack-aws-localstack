package com.aws.ws.service;

import com.aws.ws.dto.DynamoTableDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DynamoDbService {

    private final DynamoDbClient dynamoDbClient;

    public List<DynamoTableDto> listTables() {
        return dynamoDbClient.listTables().tableNames().stream()
                .map(DynamoTableDto::new)
                .collect(Collectors.toList());
    }
}
