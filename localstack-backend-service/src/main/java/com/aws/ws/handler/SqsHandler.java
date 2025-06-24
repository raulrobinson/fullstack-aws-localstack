package com.aws.ws.handler;

import com.aws.ws.models.ReceiveRequest;
import com.aws.ws.models.SendRequest;
import com.aws.ws.service.SqsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Tag(name = "SQS", description = "SQS Management")
public class SqsHandler {

    private final SqsService service;

    public Mono<ServerResponse> listQueues(ServerRequest req) {
        return service.listQueues()
                .flatMap(q -> ServerResponse.ok().bodyValue(Map.of("queues", q)));
    }

    public Mono<ServerResponse> sendMessage(ServerRequest req) {
        return req.bodyToMono(SendRequest.class)
                .flatMap(service::sendMessage)
                .then(ServerResponse.ok().bodyValue(Map.of("status", "sent")));
    }

    public Mono<ServerResponse> receiveMessages(ServerRequest req) {
        return req.bodyToMono(ReceiveRequest.class)
                .flatMap(service::receiveMessages)
                .flatMap(msgs -> ServerResponse.ok().bodyValue(Map.of("messages", msgs)));
    }
}

