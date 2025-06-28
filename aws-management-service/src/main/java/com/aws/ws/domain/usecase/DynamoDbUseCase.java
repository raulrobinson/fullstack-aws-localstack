package com.aws.ws.domain.usecase;

import com.aws.ws.domain.api.DynamoDbAdapterPort;
import com.aws.ws.domain.spi.DynamoDbServicePort;
import com.aws.ws.domain.model.DynamoItem;
import com.aws.ws.domain.model.DynamoTable;
import reactor.core.publisher.Mono;

import java.util.List;

public class DynamoDbUseCase implements DynamoDbServicePort {

    private final DynamoDbAdapterPort dynamoDbAdapterPort;

    public DynamoDbUseCase(DynamoDbAdapterPort dynamoDbAdapterPort) {
        this.dynamoDbAdapterPort = dynamoDbAdapterPort;
    }

    @Override
    public Mono<List<DynamoTable>> listTables() {
        return dynamoDbAdapterPort.listTables();
    }

    @Override
    public Mono<List<DynamoItem>> scanTable(String tableName) {
        return dynamoDbAdapterPort.scanTable(tableName);
    }
}
