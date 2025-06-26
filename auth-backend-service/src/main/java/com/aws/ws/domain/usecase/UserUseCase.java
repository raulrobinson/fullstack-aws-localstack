package com.aws.ws.domain.usecase;

import com.aws.ws.domain.api.JwtAdapterPort;
import com.aws.ws.domain.api.UserAdapterPort;
import com.aws.ws.domain.exception.*;
import com.aws.ws.domain.model.Token;
import com.aws.ws.domain.model.User;
import com.aws.ws.domain.spi.UserServicePort;
import reactor.core.publisher.Mono;

public class UserUseCase implements UserServicePort {

    private final UserAdapterPort userAdapter;
    private final JwtAdapterPort jwtAdapter;

    public UserUseCase(UserAdapterPort userAdapter, JwtAdapterPort jwtAdapter) {
        this.userAdapter = userAdapter;
        this.jwtAdapter = jwtAdapter;
    }

    @Override
    public Mono<User> createUser(User domain) {
        if (domain.getEmail() == null || domain.getPassword() == null ||
                domain.getFirstName() == null || domain.getLastName() == null || domain.getRole() == null) {
            return Mono.error(new InvalidValueException(
                    "User ID, firstName, lastName, email, password and Role",
                    "must not be null"
            ));
        }

        return userAdapter.findUserByEmail(domain.getEmail())
                .flatMap((User user) -> Mono.<User>error(new DuplicateResourceException(
                        TechnicalMessage.ALREADY_EXISTS,
                        "User already exists with email: ",
                        domain.getEmail()
                )))
                .switchIfEmpty(Mono.defer(() -> userAdapter.createUser(domain)));
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Email must not be null or empty"));
        }

        return userAdapter.findUserByEmail(email)
                .switchIfEmpty(Mono.error(new NotFoundException(TechnicalMessage.NOT_FOUND, "User not found with email: ", email)));
    }

    @Override
    public Mono<Boolean> saveToken(Token token, String email) {
        if (token.getJwt() == null || email == null) {
            return Mono.error(new InvalidValueException(
                    "Email and Token",
                    "must not be null"
            ));
        }

        return userAdapter.saveToken(token, email)
                .onErrorResume(e -> Mono.error(new BusinessException(
                        TechnicalMessage.BAD_REQUEST.getMessage(), "Error saving token: ", e.getMessage())));
    }

    @Override
    public Mono<Boolean> logout(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return Mono.error(new InvalidValueException("JWT", "must not be null or empty"));
        }

        return userAdapter.logout(jwt)
                .onErrorResume(e -> Mono.error(new BusinessException(
                        TechnicalMessage.BAD_REQUEST.getMessage(), "Error during logout: ", e.getMessage())));
    }

    @Override
    public Mono<Boolean> existsTokenByJwt(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return Mono.error(new InvalidValueException("JWT", "must not be null or empty"));
        }

        return userAdapter.existsTokenByJwt(jwt)
                .onErrorResume(e -> Mono.error(new BusinessException(
                        TechnicalMessage.BAD_REQUEST.getMessage(), "Error checking token existence: ", e.getMessage())));
    }

    @Override
    public Mono<Boolean> validateJwt(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            return Mono.error(new InvalidValueException("JWT", "must not be null or empty"));
        }

        return jwtAdapter.validateJwt(jwt)
                .onErrorResume(e -> Mono.error(new BusinessException(
                        TechnicalMessage.BAD_REQUEST.getMessage(), "Error validating JWT: ", e.getMessage())));
    }
}
