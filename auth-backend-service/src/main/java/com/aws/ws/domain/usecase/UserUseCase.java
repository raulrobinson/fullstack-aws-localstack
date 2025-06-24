package com.aws.ws.domain.usecase;

import com.aws.ws.domain.api.UserAdapter;
import com.aws.ws.domain.exception.DuplicateResourceException;
import com.aws.ws.domain.exception.InvalidValueException;
import com.aws.ws.domain.exception.TechnicalMessage;
import com.aws.ws.domain.model.User;
import com.aws.ws.domain.spi.UserServicePort;
import reactor.core.publisher.Mono;

public class UserUseCase implements UserServicePort {

    private final UserAdapter userAdapter;

    public UserUseCase(UserAdapter userAdapter) {
        this.userAdapter = userAdapter;
    }

    @Override
    public Mono<User> createUser(User domain) {
        if (domain.getEmail() == null || domain.getPassword() == null ||
            domain.getFirstName() == null || domain.getLastName() == null || domain.getRole() == null) {
            return Mono.error(new InvalidValueException("User ID, firstName, lastName, email, password and Role", "must not be null"));
        }

        return userAdapter.findUserByEmail(domain.getEmail())
                .flatMap(exists -> {
                    if (exists != null) {
                        return Mono.error(new DuplicateResourceException(
                                TechnicalMessage.ALREADY_EXISTS, "User already exists with email: ", domain.getEmail()));
                    } else {
                        return userAdapter.createUser(domain);
                    }
                });
    }

    @Override
    public Mono<User> findUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            return Mono.error(new IllegalArgumentException("Email must not be null or empty"));
        }

        return userAdapter.findUserByEmail(email)
                .switchIfEmpty(Mono.error(new RuntimeException("User not found with email: " + email)));
    }

//    @Override
//    public Mono<User> getByUserId(String userId) {
//        return userAdapter.getByUserId(userId)
//                .switchIfEmpty(Mono.error(new RuntimeException("User not found with id: " + userId)));
//    }
}
