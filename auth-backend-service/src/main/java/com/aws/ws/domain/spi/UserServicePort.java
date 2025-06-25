package com.aws.ws.domain.spi;

import com.aws.ws.domain.model.Token;
import com.aws.ws.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserServicePort {

    Mono<User> createUser(User domain);

    Mono<User> findUserByEmail(String email);

    Mono<Boolean> saveToken(Token token);
}
