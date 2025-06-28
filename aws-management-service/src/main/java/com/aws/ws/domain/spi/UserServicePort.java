package com.aws.ws.domain.spi;

import com.aws.ws.domain.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserServicePort {
    Mono<List<User>> scanTableUsers();
}
