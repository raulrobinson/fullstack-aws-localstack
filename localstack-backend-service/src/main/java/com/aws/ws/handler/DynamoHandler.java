package com.aws.ws.handler;

import com.aws.ws.common.handler.GlobalErrorHandler;
import com.aws.ws.service.DynamoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

import static com.aws.ws.common.handler.MessageHeaderHandler.getMessageId;

@Component
@RequiredArgsConstructor
@Tag(name = "DynamoDB", description = "DynamoDB Management")
public class DynamoHandler {

    private final DynamoService dynamo;
    private final GlobalErrorHandler globalErrorHandler;

    public Mono<ServerResponse> listTables(ServerRequest req) {
        return dynamo.listTables()
                .flatMap(tables -> ServerResponse.ok().bodyValue(Map.of("tables", tables)))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(req)));
    }

    public Mono<ServerResponse> listItems(ServerRequest req) {
        String table = req.pathVariable("table");
        return dynamo.listItems(table)
                .flatMap(items -> ServerResponse.ok().bodyValue(Map.of("items", items)))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(req)));
    }

    public Mono<ServerResponse> putItem(ServerRequest req) {
        String table = req.pathVariable("table");
        return req.bodyToMono(Map.class)
                .flatMap(json -> dynamo.putItem(table, json))
                .then(ServerResponse.ok().bodyValue(Map.of("status", "inserted")));
    }
}
