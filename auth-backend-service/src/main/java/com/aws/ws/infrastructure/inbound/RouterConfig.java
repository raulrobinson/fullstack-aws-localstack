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
                    path = "/api/users/login",
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
                    path = "/api/users/register",
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
                .path("/api/users", builder -> builder
                        .POST("/register", accept(MediaType.APPLICATION_JSON), userHandler::register)
                        .POST("/login", accept(MediaType.APPLICATION_JSON), userHandler::login)
//                        .GET("/me", userHandler::getProfile) // requiere JWT
//                        .PUT("/update", userHandler::updateUser) // requiere JWT
//                        .POST("/logout", userHandler::logout) // requiere JWT
                )
                .build();
    }

//    @Bean
//    @RouterOperations({
//            // Define the route for getting a user by ID
//            @org.springdoc.core.annotations.RouterOperation(
//                    path = "/v1/users/{id}",
//                    produces = "application/json",
//                    method = org.springframework.web.bind.annotation.RequestMethod.GET,
//                    beanClass = UserHandler.class,
//                    beanMethod = "getByUserId",
//                    operation = @io.swagger.v3.oas.annotations.Operation(
//                            operationId = "getByUserId",
//                            summary = "Get User by ID",
//                            description = "Retrieve User by ID from the database.",
//                            parameters = @io.swagger.v3.oas.annotations.Parameter(
//                                    name = "id",
//                                    description = "ID of the User to retrieve",
//                                    required = true,
//                                    in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
//                                    example = "user_12345" // Example ID for documentation purposes
//                            )
//                    )
//            ),
//            // Create a user request UserDTO.class
//            @org.springdoc.core.annotations.RouterOperation(
//                    path = "/v1/users",
//                    produces = "application/json",
//                    method = org.springframework.web.bind.annotation.RequestMethod.POST,
//                    beanClass = UserHandler.class,
//                    beanMethod = "createUser",
//                    operation = @io.swagger.v3.oas.annotations.Operation(
//                            operationId = "createUser",
//                            summary = "Create User",
//                            description = "Create a new User in the database.",
//                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                                    required = true,
//                                    description = "User DTO for creating a new user",
//                                    content = @io.swagger.v3.oas.annotations.media.Content(
//                                            mediaType = "application/json",
//                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UserDTO.class)
//                                    )
//                            )
//                    )
//            ),
//            // Define the route for finding a user by email
//            @org.springdoc.core.annotations.RouterOperation(
//                    path = "/v1/users/{email}",
//                    produces = "application/json",
//                    method = org.springframework.web.bind.annotation.RequestMethod.GET,
//                    beanClass = UserHandler.class,
//                    beanMethod = "findUserByEmail",
//                    operation = @io.swagger.v3.oas.annotations.Operation(
//                            operationId = "findUserByEmail",
//                            summary = "Find User by Email",
//                            description = "Retrieve User by Email from the database.",
//                            parameters = @io.swagger.v3.oas.annotations.Parameter(
//                                    name = "email",
//                                    description = "Email of the User to retrieve",
//                                    required = true,
//                                    in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
//                                    example = "rasysbox@hotmail.com"
//                            )
//                    )
//            )
//    })
//    public RouterFunction<ServerResponse> route(UserHandler handler) {
//        return org.springframework.web.reactive.function.server.RouterFunctions
//                .route()
//                .GET("/v1/users/{email}", handler::findUserByEmail)
//                .POST("/v1/users", handler::createUser)
////                .GET("/v1/users/{id}", handler::getByUserId)
////                .PUT("/users/{id}", handler::updateUser)
////                .DELETE("/users/{id}", handler::deleteUser)
//                .build();
//    }
}
