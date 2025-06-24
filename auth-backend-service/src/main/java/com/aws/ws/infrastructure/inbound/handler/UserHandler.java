package com.aws.ws.infrastructure.inbound.handler;

import com.aws.ws.domain.spi.UserServicePort;
import com.aws.ws.infrastructure.common.handler.GlobalErrorHandler;
import com.aws.ws.infrastructure.common.util.JwtUtil;
import com.aws.ws.infrastructure.inbound.dto.LoginRequest;
import com.aws.ws.infrastructure.inbound.dto.UserDTO;
import com.aws.ws.infrastructure.inbound.enums.Roles;
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
    private final JwtUtil jwtUtil;

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

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> servicePort.findUserByEmail(login.getEmail())
                        .flatMap(user -> {
                            if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
                                String token = jwtUtil.generateToken(user.getEmail(), List.of(user.getRole()));
                                return ServerResponse.ok().bodyValue(Map.of("token", token));
                            } else {
                                return ServerResponse.status(HttpStatus.UNAUTHORIZED).build();
                            }
                        }));
    }

    public Mono<ServerResponse> register(ServerRequest request) {
        return request.bodyToMono(LoginRequest.class)
                .flatMap(login -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setEmail(login.getEmail());
                    userDTO.setPassword(passwordEncoder.encode(login.getPassword()));
                    userDTO.setFirstName(login.getFirstName());
                    userDTO.setLastName(login.getLastName());
                    userDTO.setRole(Roles.USER.getRoleName());

                    return servicePort.createUser(mapper.toDomain(userDTO))
                            .flatMap(createdUser -> ServerResponse.status(HttpStatus.CREATED).bodyValue(createdUser));
                })
                .doOnError(error -> log.error("❌ Error registering user: {}", error.getMessage()))
                .onErrorResume(exception -> globalErrorHandler.handle(exception, getMessageId(request)));
    }


}
