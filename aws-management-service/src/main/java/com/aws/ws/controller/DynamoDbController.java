package com.aws.ws.controller;

import com.aws.ws.dto.DynamoTableDto;
import com.aws.ws.service.DynamoDbService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws/dynamodb")
@RequiredArgsConstructor
public class DynamoDbController {

    private final DynamoDbService dynamoDbService;

    @GetMapping("/tables")
    public List<DynamoTableDto> listTables() {
        return dynamoDbService.listTables();
    }
}
