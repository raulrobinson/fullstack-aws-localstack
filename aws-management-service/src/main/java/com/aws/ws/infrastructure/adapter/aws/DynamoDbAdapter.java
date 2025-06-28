package com.aws.ws.infrastructure.adapter.aws;

import com.aws.ws.domain.model.DynamoItem;
import com.aws.ws.domain.model.DynamoTable;
import com.aws.ws.domain.api.DynamoDbAdapterPort;
import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.mapper.DynamoDbMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DynamoDbAdapter implements DynamoDbAdapterPort {

    private final DynamoDbClient dynamoDbClient;
    private final DynamoDbMapper mapper;

    @Override
    public Mono<List<DynamoTable>> listTables() {
         return Mono.fromCallable(() -> dynamoDbClient.listTables().tableNames().stream()
                 .map(DynamoTable::new)
                 .collect(Collectors.toList()));
    }

    @Override
    public Mono<List<DynamoItem>> scanTable(String tableName) {
        return Mono.fromCallable(() -> {
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .build();

            return dynamoDbClient.scan(scanRequest).items().stream()
                    .map(this::convertToDto)
                    .collect(Collectors.toList());
        });
    }

    @Override
    public Mono<List<User>> scanTableUsers() {
        return Mono.fromCallable(() -> {
            var scanRequest = ScanRequest.builder().tableName("Users").build();
            return dynamoDbClient.scan(scanRequest).items().stream()
                    .map(item -> new User(
                            item.get("userId").s(),
                            item.get("userName").s(),
                            item.get("userLastName").s(),
                            item.get("userEmail").s(),
                            item.get("userPassword").s(),
                            item.get("userRole").s()
                    ))
                    .collect(Collectors.toList());
        }).doOnError(e -> {
            System.err.println("❌ Error escaneando: " + e.getMessage());
        });
    }

    private DynamoItem convertToDto(Map<String, AttributeValue> item) {
        Map<String, String> attributes = item.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue().s() != null ? e.getValue().s() : e.getValue().toString()
                ));
        return new DynamoItem(attributes);
    }

//    private User convertToUser(Map<String, AttributeValue> item) {
//        Map<String, String> attributes = item.entrySet().stream()
//                .collect(Collectors.toMap(
//                        Map.Entry::getKey,
//                        e -> e.getValue().s() != null ? e.getValue().s() : e.getValue().toString()
//                ));
//        return mapper.mapToUser(attributes);
//    }
}
