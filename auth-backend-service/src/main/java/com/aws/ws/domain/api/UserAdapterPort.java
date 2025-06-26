package com.aws.ws.domain.api;

import com.aws.ws.domain.model.Token;
import com.aws.ws.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserAdapterPort {

    Mono<User> createUser(User domain);

    Mono<User> findUserByEmail(String email);

    Mono<Boolean> saveToken(Token token, String email);

    Mono<Boolean> existsTokenByJwt(String jwt);

    Mono<Boolean> logout(String jwt);
}
