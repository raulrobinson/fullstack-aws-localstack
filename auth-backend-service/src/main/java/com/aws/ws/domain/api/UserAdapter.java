package com.aws.ws.domain.api;

import com.aws.ws.domain.model.User;
import reactor.core.publisher.Mono;

public interface UserAdapter {
//    Mono<Boolean> existsByEmail(String email);
//
//    Mono<User> getByUserId(String userId);

    Mono<User> createUser(User domain);

    Mono<User> findUserByEmail(String email);
}
