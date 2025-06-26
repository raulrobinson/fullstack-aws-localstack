package com.aws.ws.application.config;

import com.aws.ws.domain.api.JwtAdapterPort;
import com.aws.ws.domain.api.UserAdapterPort;
import com.aws.ws.domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public UserUseCase catalogUseCase(UserAdapterPort userAdapter, JwtAdapterPort jwtAdapter) {
        return new UserUseCase(userAdapter, jwtAdapter);
    }

}
