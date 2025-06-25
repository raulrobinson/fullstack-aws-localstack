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

}
