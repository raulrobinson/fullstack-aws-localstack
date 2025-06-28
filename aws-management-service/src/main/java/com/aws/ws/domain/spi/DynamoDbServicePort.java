package com.aws.ws.domain.spi;

import com.aws.ws.domain.model.DynamoItem;
import com.aws.ws.domain.model.DynamoTable;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DynamoDbServicePort {
    Mono<List<DynamoTable>> listTables();

    Mono<List<DynamoItem>> scanTable(String tableName);
}
