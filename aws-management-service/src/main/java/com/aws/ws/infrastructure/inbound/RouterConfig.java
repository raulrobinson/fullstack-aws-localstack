package com.aws.ws.infrastructure.inbound;

import com.aws.ws.domain.model.DynamoItem;
import com.aws.ws.domain.model.DynamoTable;
import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.inbound.handler.DynamoDbHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfig {

    @Bean
    @RouterOperations({
            // Define a route for listing all DynamoDB tables
            @RouterOperation(
                    path = "/aws/dynamodb/tables",
                    beanClass = DynamoDbHandler.class,
                    method = RequestMethod.GET,
                    beanMethod = "listTables",
                    operation = @Operation(
                            operationId = "listTables",
                            summary = "List DynamoDB Tables",
                            description = "Retrieves a list of all DynamoDB tables in the AWS account.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK",
                                            content = @io.swagger.v3.oas.annotations.media.Content(
                                                    mediaType = "application/json",
                                                    array = @io.swagger.v3.oas.annotations.media.ArraySchema(
                                                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = DynamoTable.class)
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            // Define a route for scanning a specific DynamoDB table
            @RouterOperation(
                    path = "/aws/dynamodb/scan/{tableName}",
                    beanClass = DynamoDbHandler.class,
                    method = RequestMethod.GET,
                    beanMethod = "scanTable",
                    operation = @Operation(
                            operationId = "scanTable",
                            summary = "Scan DynamoDB Table",
                            description = "Scans a specific DynamoDB table and returns its items.",
                            parameters = @Parameter(
                                    name = "tableName",
                                    description = "Table name to retrieve",
                                    required = true,
                                    in = io.swagger.v3.oas.annotations.enums.ParameterIn.PATH,
                                    example = "Users"
                            ),
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    array = @ArraySchema(
                                                            schema = @Schema(implementation = DynamoItem.class)
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Table not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            ),
            // Define a route for scanning the "Users" table
            @RouterOperation(
                    path = "/aws/dynamodb/scan/table/users",
                    beanClass = DynamoDbHandler.class,
                    method = RequestMethod.GET,
                    beanMethod = "scanTableUsers",
                    operation = @Operation(
                            operationId = "scanTableUsers",
                            summary = "Scan Users Table",
                            description = "Scans the 'Users' table and returns its items.",
                            responses = {
                                    @ApiResponse(
                                            responseCode = "200",
                                            description = "OK",
                                            content = @Content(
                                                    mediaType = "application/json",
                                                    array = @ArraySchema(
                                                            schema = @Schema(implementation = User.class)
                                                    )
                                            )
                                    ),
                                    @ApiResponse(responseCode = "404", description = "Table not found"),
                                    @ApiResponse(responseCode = "500", description = "Internal server error")
                            }
                    )
            )
    })
    public RouterFunction<ServerResponse> route(DynamoDbHandler dynamoDbHandler) {
        return RouterFunctions.route()
                .GET("/aws/dynamodb/tables", dynamoDbHandler::listTables)
                .GET("/aws/dynamodb/scan/{tableName}", dynamoDbHandler::scanTable)
                .GET("/aws/dynamodb/scan/table/users", dynamoDbHandler::scanTableUsers)
                .build();
    }
}
