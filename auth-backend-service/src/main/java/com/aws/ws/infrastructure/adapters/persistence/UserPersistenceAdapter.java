package com.aws.ws.infrastructure.adapters.persistence;

import com.aws.ws.domain.api.UserAdapter;
import com.aws.ws.domain.model.Token;
import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.adapters.persistence.constants.UserDefinition;
import com.aws.ws.infrastructure.adapters.persistence.mapper.UserPersistenceMapper;
import com.aws.ws.infrastructure.inbound.enums.Roles;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserAdapter {

    @Value("${aws.dynamodb.table.users}")
    private String tableUsers;

    @Value("${aws.dynamodb.table.tokens}")
    private String tableTokens;

    private final DynamoDbAsyncClient client;
    private final PasswordEncoder passwordEncoder;
    private final UserPersistenceMapper mapper;

    @Override
    public Mono<User> createUser(User user) {
        if (user.getUserId() == null) {
            user.setUserId(UUID.randomUUID().toString());
        }
        log.info("Creating user with ID: {}", user.getUserId());

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableUsers)
                .item(Map.of(
                        UserDefinition.USER_ID, AttributeValue.builder().s(user.getUserId()).build(),
                        UserDefinition.USER_EMAIL, AttributeValue.builder().s(newUser.getEmail()).build(),
                        UserDefinition.USER_FIRSTNAME, AttributeValue.builder().s(newUser.getFirstName()).build(),
                        UserDefinition.USER_LASTNAME, AttributeValue.builder().s(newUser.getLastName()).build(),
                        UserDefinition.USER_PASSWORD, AttributeValue.builder().s(newUser.getPassword()).build(),
                        UserDefinition.USER_ROLE, AttributeValue.builder().s(Roles.USER.getRoleName()).build()
                ))
                .build();

        return Mono.fromFuture(() -> client.putItem(request))
                .thenReturn(user);
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        ScanRequest request = ScanRequest.builder()
                .tableName(tableUsers)
                .filterExpression(UserDefinition.USER_EMAIL + " = :emailVal")
                .expressionAttributeValues(Map.of(
                        ":emailVal", AttributeValue.builder().s(email).build()
                ))
                .build();

        return Mono.fromFuture(() -> client.scan(request))
                .flatMap(scanResponse -> {
                    List<Map<String, AttributeValue>> items = scanResponse.items();
                    if (items == null || items.isEmpty()) {
                        log.info("✅ No user found with email: {}", email);
                        return Mono.empty();
                    }
                    log.info("✅ User found: {}", items.getFirst());
                    return Mono.just(mapper.toUser(items.getFirst()));
                })
                .doOnError(e -> log.error("❌ Error scanning DynamoDB for email {}: {}", email, e.getMessage()));
    }

    @Override
    public Mono<Boolean> saveToken(Token token) {
        if (token.getTokenId() == null) {
            token.setTokenId(UUID.randomUUID().toString());
        }
        log.info("Creating token with Token ID: {}", token.getTokenId());

        // 1. Desactivar tokens anteriores
        return deactivateActiveTokensByUserId(token.getUserId())
                // 2. Guardar el nuevo token
                .then(Mono.fromFuture(() -> client.putItem(
                        PutItemRequest.builder()
                                .tableName(tableTokens)
                                .item(Map.of(
                                        UserDefinition.TOKEN_ID, AttributeValue.builder().s(token.getTokenId()).build(),
                                        UserDefinition.TOKEN_USER_ID, AttributeValue.builder().s(token.getUserId()).build(),
                                        UserDefinition.TOKEN_JWT, AttributeValue.builder().s(token.getJwt()).build(),
                                        UserDefinition.TOKEN_CREATED_DATE, AttributeValue.builder().s(token.getIssuedAt().toString()).build(),
                                        UserDefinition.TOKEN_EXPIRATION_DATE, AttributeValue.builder().s(token.getExpiresAt().toString()).build(),
                                        UserDefinition.TOKEN_ACTIVE, AttributeValue.builder().bool(true).build()
                                ))
                                .build()
                ))).thenReturn(true);
    }

    private Mono<Void> deactivateActiveTokensByUserId(String userId) {
        ScanRequest scanRequest = ScanRequest.builder()
                .tableName(tableTokens)
                .filterExpression("userId = :uid AND active = :act")
                .expressionAttributeValues(Map.of(
                        ":uid", AttributeValue.builder().s(userId).build(),
                        ":act", AttributeValue.builder().bool(true).build()
                ))
                .build();

        return Mono.fromFuture(() -> client.scan(scanRequest))
                .flatMapMany(response -> Flux.fromIterable(response.items()))
                .flatMap(item -> {
                    String tokenId = item.get(UserDefinition.TOKEN_ID).s(); // ✅ se usa la constante correctamente
                    Map<String, AttributeValueUpdate> updates = Map.of(
                            "active", AttributeValueUpdate.builder()
                                    .value(AttributeValue.builder().bool(false).build())
                                    .action(AttributeAction.PUT)
                                    .build()
                    );

                    UpdateItemRequest updateRequest = UpdateItemRequest.builder()
                            .tableName(tableTokens)
                            .key(Map.of(
                                    UserDefinition.TOKEN_ID, AttributeValue.builder().s(tokenId).build() // ✅ clave correcta
                            ))
                            .attributeUpdates(updates)
                            .build();

                    return Mono.fromFuture(() -> client.updateItem(updateRequest)).then();
                })
                .then(); // Mono<Void>
    }

}
