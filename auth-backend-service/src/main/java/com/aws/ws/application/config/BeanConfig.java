package com.aws.ws.application.config;

import com.aws.ws.domain.api.UserAdapter;
import com.aws.ws.domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserUseCase catalogUseCase(UserAdapter userAdapter) {
        return new UserUseCase(userAdapter);
    }

//    @Bean
//    public CatalogPersistenceAdapter catalogPersistenceAdapter(DynamoDbAsyncClient client) {
//        return new CatalogPersistenceAdapter(client);
//    }

}
