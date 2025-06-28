package com.aws.ws.domain.api;

import com.aws.ws.domain.model.DynamoItem;
import com.aws.ws.domain.model.DynamoTable;
import com.aws.ws.domain.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DynamoDbAdapterPort {
    Mono<List<DynamoTable>> listTables();

    Mono<List<DynamoItem>> scanTable(String tableName);

    Mono<List<User>> scanTableUsers();
}
