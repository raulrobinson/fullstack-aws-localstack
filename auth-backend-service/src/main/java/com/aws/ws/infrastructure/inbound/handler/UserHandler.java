package com.aws.ws.infrastructure.inbound.handler;

import com.aws.ws.domain.spi.UserServicePort;
import com.aws.ws.infrastructure.common.handler.GlobalErrorHandler;
import com.aws.ws.infrastructure.adapters.jwt.JwtAdapter;
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
    private final JwtAdapter jwtAdapter;

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
                            // Aquí generamos el token JWT
                            return jwtAdapter.generateToken(user.getEmail(), List.of(user.getRole()))
                                    .flatMap(token -> servicePort.saveToken(token, user.getEmail())
                                            .flatMap(success -> {
                                                if (success) {
                                                    return ServerResponse.ok().bodyValue(Map.of(
                                                            "token", token.getJwt(),
                                                            "messageId", getMessageId(request)
                                                    ));
                                                } else {
                                                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                                            .bodyValue(Map.of("error", "Failed to save token"));
                                                }
                                            }));
                        }));
    }

    public Mono<ServerResponse> logout(ServerRequest request) {
        String token = request.headers().firstHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }
        String jwt = token.substring(7); // Remove "Bearer " prefix
        return servicePort.existsTokenByJwt(jwt)
                .flatMap(existingToken -> servicePort.logout(jwt)
                        .flatMap(success -> {
                            if (success) {
                                return ServerResponse.ok().bodyValue(Map.of(
                                        "message", "Logout successful",
                                        "messageId", getMessageId(request)
                                ));
                            } else {
                                return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .bodyValue(Map.of("error", "Failed to logout"));
                            }
                        }))
                .switchIfEmpty(ServerResponse.status(HttpStatus.UNAUTHORIZED).build())
                .doOnError(error -> log.error("❌ Error during logout: {}", error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }

    public Mono<ServerResponse> validateJwt(ServerRequest request) {
        String token = request.headers().firstHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
        }

        String jwt = token.substring(7); // Remove "Bearer " prefix

        return servicePort.existsTokenByJwt(jwt)
                .flatMap(exists -> {
                    if (!exists) {
                        return ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue(Map.of(
                                "valid", false,
                                "messageId", getMessageId(request)
                        ));
                    }

                    // Si existe, validamos la firma y expiración
                    return servicePort.validateJwt(jwt)
                            .flatMap(valid -> {
                                if (valid) {
                                    return ServerResponse.ok().bodyValue(Map.of(
                                            "valid", true,
                                            "messageId", getMessageId(request)
                                    ));
                                } else {
                                    return ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue(Map.of(
                                            "valid", false,
                                            "messageId", getMessageId(request)
                                    ));
                                }
                            });
                })
                .switchIfEmpty( // Si no existe el token en DB
                        ServerResponse.status(HttpStatus.UNAUTHORIZED).bodyValue(Map.of(
                                "valid", false,
                                "messageId", getMessageId(request)
                        ))
                )
                .doOnError(error -> log.error("❌ Error validating JWT: {}", error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }

}
