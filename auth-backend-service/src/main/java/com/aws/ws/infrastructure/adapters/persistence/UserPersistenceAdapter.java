package com.aws.ws.infrastructure.adapters.persistence;

import com.aws.ws.domain.api.UserAdapter;
import com.aws.ws.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.Map;
import java.util.UUID;

@Service
public class UserPersistenceAdapter implements UserAdapter {

    @Value("${aws.dynamodb.table-name}")
    private String tableName;

    private final DynamoDbAsyncClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public UserPersistenceAdapter(DynamoDbAsyncClient client) {
        this.client = client;
    }

    @Override
    public Mono<User> createUser(User user) {
        if (user.getID() == null) {
            user.setID(UUID.randomUUID().toString());
        }

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(Map.of(
                        "ID", AttributeValue.builder().s(user.getID()).build(),
                        "email", AttributeValue.builder().s(user.getEmail()).build(),
                        "firstName", AttributeValue.builder().s(user.getFirstName()).build(),
                        "lastName", AttributeValue.builder().s(user.getLastName()).build(),
                        "password", AttributeValue.builder().s(user.getPassword()).build(),
                        "role", AttributeValue.builder().s(user.getRole()).build()
                ))
                .build();

        return Mono.fromFuture(() -> client.putItem(request))
                .thenReturn(user);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        ScanRequest request = ScanRequest.builder()
                .tableName(tableName)
                .filterExpression("email = :emailVal")
                .expressionAttributeValues(Map.of(":emailVal", AttributeValue.builder().s(email).build()))
                .build();

        return Mono.fromFuture(() -> client.scan(request))
                .map(ScanResponse::items)
                .mapNotNull(items -> items.isEmpty() ? null : convert(items.getFirst()));
    }

    private User convert(Map<String, AttributeValue> item) {
        User user = new User();
        user.setID(item.get("ID").s());
        user.setEmail(item.get("email").s());
        user.setFirstName(item.get("firstName").s());
        user.setLastName(item.get("lastName").s());
        user.setPassword(item.get("password").s());
        user.setRole(item.get("role").s());
        return user;
    }

//    @Override
//    public Mono<Boolean> existsByEmail(String email) {
//        Map<String, AttributeValue> key = new HashMap<>();
//        key.put("email", AttributeValue.builder().s(email).build());
//
//        GetItemRequest request = GetItemRequest.builder()
//                .tableName(tableName)
//                .key(key)
//                .build();
//
//        CompletableFuture<GetItemResponse> future = client.getItem(request);
//
//        return Mono.fromFuture(future)
//                .map(GetItemResponse::hasItem)
//                .onErrorReturn(false); // Si ocurre un error, asumimos que el usuario no existe
//    }
//
//    @Override
//    public Mono<User> getByUserId(String userId) {
//        Map<String, AttributeValue> key = new HashMap<>();
//        key.put("ID", AttributeValue.builder().s(userId).build());
//
//        GetItemRequest request = GetItemRequest.builder()
//                .tableName(tableName)
//                .key(key)
//                .build();
//
//        CompletableFuture<GetItemResponse> future = client.getItem(request);
//
//        return Mono.fromFuture(future)
//                .handle((response, sink) -> {
//                    if (!response.hasItem()) {
//                        sink.error(new NotFoundException(
//                                TechnicalMessage.NOT_FOUND, "Catalog not found with catalogId: ", userId));
//                        return;
//                    }
//
//                    Map<String, AttributeValue> item = response.item();
//
//                    User user = new User();
//                    //user.setCatalogId(item.get("ID").s()); // ✅ ID for catalogId
//                    // user.setCatalogName(item.get("catalogName").s());
//
//                    try {
//                        String itemsJson = item.get("items").s();
//                        //user.setItems(mapper.readValue(itemsJson, new TypeReference<>() {}));
//                    } catch (Exception e) {
//                        sink.error(new RuntimeException("Error parsing items", e));
//                        return;
//                    }
//                    sink.next(user);
//                });
//    }
}
