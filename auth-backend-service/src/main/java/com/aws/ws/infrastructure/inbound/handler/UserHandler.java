package com.aws.ws.infrastructure.inbound.handler;

import com.aws.ws.domain.model.Token;
import com.aws.ws.domain.spi.UserServicePort;
import com.aws.ws.infrastructure.common.handler.GlobalErrorHandler;
import com.aws.ws.infrastructure.common.util.JwtUtil;
import com.aws.ws.infrastructure.inbound.dto.LoginRequest;
import com.aws.ws.infrastructure.inbound.mapper.UserMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import static com.aws.ws.infrastructure.common.handler.MessageHeaderHandler.getMessageId;

@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "Users", description = "Users Management")
public class UserHandler {

    private final UserServicePort servicePort;
    private final GlobalErrorHandler globalErrorHandler;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> servicePort.createUser(mapper.toDomain(login))
                        .flatMap(createdUser -> ServerResponse.status(HttpStatus.CREATED).bodyValue(createdUser)))
                .doOnError(error -> log.error("❌ Error registering user: {}", error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> servicePort.findUserByEmail(login.getEmail())
                        .flatMap(user -> {
                            if (!passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                            }

                            String token = jwtUtil.generateToken(user.getEmail(), List.of(user.getRole()));

                            Token tokenToSave = Token.builder()
                                    .jwt(token)
                                    .userId(user.getUserId())
                                    .issuedAt(Instant.now())
                                    .expiresAt(Instant.now().plusSeconds(3600))
                                    .active(true)
                                    .build();

                            return servicePort.saveToken(tokenToSave)
                                    .flatMap(success -> {
                                        if (success) {
                                            return ServerResponse.ok().bodyValue(Map.of(
                                                    "token", token,
                                                    "messageId", getMessageId(request)
                                            ));
                                        } else {
                                            return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                    .bodyValue(Map.of("error", "Failed to save token"));
                                        }
                                    });
                        }));
    }

}
