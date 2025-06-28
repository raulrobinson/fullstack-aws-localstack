package com.aws.ws.application.config;

import com.aws.ws.domain.api.DynamoDbAdapterPort;
import com.aws.ws.domain.usecase.DynamoDbUseCase;
import com.aws.ws.domain.usecase.dynamodb.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public DynamoDbUseCase dynamoDbUseCase(DynamoDbAdapterPort dynamoDbAdapterPort) {
        return new DynamoDbUseCase(dynamoDbAdapterPort);
    }

    @Bean
    public UserUseCase userUseCase(DynamoDbAdapterPort dynamoDbAdapterPort) {
        return new UserUseCase(dynamoDbAdapterPort);
    }
}
