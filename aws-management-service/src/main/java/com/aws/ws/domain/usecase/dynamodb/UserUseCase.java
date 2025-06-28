package com.aws.ws.domain.usecase.dynamodb;

import com.aws.ws.domain.api.DynamoDbAdapterPort;
import com.aws.ws.domain.model.User;
import com.aws.ws.domain.spi.UserServicePort;
import reactor.core.publisher.Mono;

import java.util.List;

public class UserUseCase implements UserServicePort {

    private final DynamoDbAdapterPort dynamoDbAdapterPort;

    public UserUseCase(DynamoDbAdapterPort dynamoDbAdapterPort) {
        this.dynamoDbAdapterPort = dynamoDbAdapterPort;
    }

    @Override
    public Mono<List<User>> scanTableUsers() {
        return dynamoDbAdapterPort.scanTableUsers();
    }
}
