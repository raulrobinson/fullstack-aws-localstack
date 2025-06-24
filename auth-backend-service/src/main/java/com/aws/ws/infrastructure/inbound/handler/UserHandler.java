package com.aws.ws.infrastructure.inbound.handler;

import com.aws.ws.domain.spi.UserServicePort;
import com.aws.ws.infrastructure.common.handler.GlobalErrorHandler;
import com.aws.ws.infrastructure.inbound.dto.UserDTO;
import com.aws.ws.infrastructure.inbound.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static com.aws.ws.infrastructure.common.handler.MessageHeaderHandler.getMessageId;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users Management")
public class UserHandler {

    private final UserServicePort servicePort;
    private final GlobalErrorHandler globalErrorHandler;
    private final UserMapper mapper;

//    // Method to handle fetching user by ID
//    public Mono<ServerResponse> getByUserId(ServerRequest request) {
//        String userId = request.pathVariable("userId");
//        return servicePort.getByUserId(userId)
//                .flatMap(user -> ServerResponse.ok().bodyValue(user))
//                .doOnError(error -> log.error("Error fetching user with ID {}: {}", userId, error.getMessage()))
//                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
//    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        log.debug("Creating new user");
        return request.bodyToMono(UserDTO.class)
                .flatMap(userDTO -> servicePort.createUser(mapper.toDomain(userDTO)))
                .flatMap(user -> ServerResponse.status(HttpStatus.CREATED).bodyValue(user))
                .doOnError(error -> log.error("Error creating user: {}", error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }

    public Mono<ServerResponse> findUserByEmail(ServerRequest request) {
        String email = request.pathVariable("email");
        return servicePort.findUserByEmail(email)
                .flatMap(user -> ServerResponse.ok().bodyValue(user))
                .doOnError(error -> log.error("Error finding user with email {}: {}", email, error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }
}
