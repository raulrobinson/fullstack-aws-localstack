package com.aws.ws.domain.api;

import com.aws.ws.domain.model.Token;
import reactor.core.publisher.Mono;

import java.util.List;

public interface JwtAdapterPort {
    Mono<Token> generateToken(String email, List<String> roles);

    Mono<Boolean> validateJwt(String jwt);
}
