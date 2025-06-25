package com.aws.ws.infrastructure.inbound;

import com.aws.ws.infrastructure.inbound.dto.LoginRequest;
import com.aws.ws.infrastructure.inbound.handler.UserHandler;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterConfig {

    @Bean
    @RouterOperations({
            // Define the route for user login
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/auth/login",
                    produces = "application/json",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "login",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "loginUser",
                            summary = "User Login",
                            description = "Authenticate a user and return a JWT token.",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    description = "Login credentials for the user",
                                    content = @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequest.class)
                                    )
                            )
                    )
            ),
            // Define the route for user registration
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/auth/register",
                    produces = "application/json",
                    method = org.springframework.web.bind.annotation.RequestMethod.POST,
                    beanClass = UserHandler.class,
                    beanMethod = "register",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "registerUser",
                            summary = "User Registration",
                            description = "Register a new user in the system.",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    description = "User registration details",
                                    content = @io.swagger.v3.oas.annotations.media.Content(
                                            mediaType = "application/json",
                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = LoginRequest.class)
                                    )
                            )
                    )
            )
    })
    public RouterFunction<?> userRoutes(UserHandler userHandler) {
        return route()
                .path("/api/auth", builder -> builder
                        .POST("/register", accept(MediaType.APPLICATION_JSON), userHandler::register)
                        .POST("/login", accept(MediaType.APPLICATION_JSON), userHandler::login)
//                        .GET("/me", userHandler::getProfile) // requiere JWT
//                        .PUT("/update", userHandler::updateUser) // requiere JWT
//                        .POST("/logout", userHandler::logout) // requiere JWT
                )
                .build();
    }
}
