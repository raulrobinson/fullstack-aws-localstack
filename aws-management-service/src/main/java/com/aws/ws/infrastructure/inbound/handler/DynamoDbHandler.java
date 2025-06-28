package com.aws.ws.infrastructure.inbound.handler;

import com.aws.ws.domain.spi.DynamoDbServicePort;
import com.aws.ws.domain.spi.UserServicePort;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "DynamoDB Management", description = "Endpoints for managing DynamoDB tables")
public class DynamoDbHandler {

    private final DynamoDbServicePort servicePort;
    private final UserServicePort userServicePort;

    public Mono<ServerResponse> listTables(ServerRequest request) {
        log.info("Received request to list DynamoDB tables");
        return servicePort.listTables()
                .flatMap(tables -> ServerResponse.ok().bodyValue(tables))
                .onErrorResume(e -> {
                    log.error("Error listing DynamoDB tables", e);
                    return ServerResponse.status(500).bodyValue("Error listing tables: " + e.getMessage());
                });
    }

    public Mono<ServerResponse> scanTable(ServerRequest request) {
        String tableName = request.pathVariable("tableName");
        log.info("Received request to scan DynamoDB table: {}", tableName);
        return servicePort.scanTable(tableName)
                .flatMap(items -> ServerResponse.ok().bodyValue(items))
                .onErrorResume(e -> {
                    log.error("Error scanning DynamoDB table: {}", tableName, e);
                    return ServerResponse.status(500).bodyValue("Error scanning table: " + e.getMessage());
                });
    }

    public Mono<ServerResponse> scanTableUsers(ServerRequest request) {
        log.info("Received request to scan DynamoDB table for users: {}", "Users");
        return userServicePort.scanTableUsers()
                .flatMap(items -> ServerResponse.ok().bodyValue(items))
                .onErrorResume(e -> {
                    log.error("Error scanning DynamoDB table for users: {}", "Users", e);
                    return ServerResponse.status(500).bodyValue("Error scanning table: " + e.getMessage());
                });
    }
}
