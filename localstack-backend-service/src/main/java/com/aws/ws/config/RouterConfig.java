package com.aws.ws.config;

import com.aws.ws.handler.DynamoHandler;
import com.aws.ws.handler.SqsHandler;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.WebFilter;

@Configuration
public class RouterConfig {

    @Bean
    @RouterOperations({
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/sqs/list",
                    method = RequestMethod.GET,
                    beanClass = SqsHandler.class,
                    beanMethod = "listQueues",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "listQueues",
                            summary = "List SQS Queues",
                            description = "Retrieve a list of all SQS queues."
                    )
            ),
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/sqs/send",
                    method = RequestMethod.POST,
                    beanClass = SqsHandler.class,
                    beanMethod = "sendMessage",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "sendMessage",
                            summary = "Send Message to SQS Queue",
                            description = "Send a message to an SQS queue.",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    description = "Message to send to the queue"
                            )
                    )
            ),
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/sqs/receive",
                    method = RequestMethod.POST,
                    beanClass = SqsHandler.class,
                    beanMethod = "receiveMessages",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "receiveMessages",
                            summary = "Receive Messages from SQS Queue",
                            description = "Receive messages from an SQS queue.",
                            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                    required = true,
                                    description = "Receive request parameters"
                            )
                    )
            ),
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/dynamodb/tables",
                    method = RequestMethod.GET,
                    beanClass = DynamoHandler.class,
                    beanMethod = "listTables",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "listTables",
                            summary = "List DynamoDB Tables",
                            description = "Retrieve a list of all DynamoDB tables."
                    )
            ),
            @org.springdoc.core.annotations.RouterOperation(
                    path = "/api/dynamodb/items/{table}",
                    method = RequestMethod.GET,
                    beanClass = DynamoHandler.class,
                    beanMethod = "listItems",
                    operation = @io.swagger.v3.oas.annotations.Operation(
                            operationId = "listItems",
                            summary = "List Items in DynamoDB Table",
                            description = "Retrieve items from a specified DynamoDB table.",
                            parameters = @io.swagger.v3.oas.annotations.Parameter(
                                    name = "table",
                                    description = "Name of the DynamoDB table to retrieve items from",
                                    required = true,
                                    in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH
                            )
                    )
            )
    })
    public RouterFunction<ServerResponse> routes(SqsHandler sqs, DynamoHandler dynamo) {
        return RouterFunctions
                .route()
                .path("/api/sqs", builder -> builder
                        .GET("/list", sqs::listQueues)
                        .POST("/send", sqs::sendMessage)
                        .POST("/receive", sqs::receiveMessages)
                )
                .path("/api/dynamodb", builder -> builder
                        .GET("/tables", dynamo::listTables)
                        .GET("/items/{table}", dynamo::listItems)
                )
                .build();
    }

    @Bean
    public WebFilter corsFilter() {
        return (exchange, chain) -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Access-Control-Allow-Origin", "*");
            response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
            if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
                response.setStatusCode(HttpStatus.OK);
                return response.setComplete();
            }
            return chain.filter(exchange);
        };
    }

}

