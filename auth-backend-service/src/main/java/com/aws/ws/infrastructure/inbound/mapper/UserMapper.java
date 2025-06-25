package com.aws.ws.infrastructure.inbound.mapper;

import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.inbound.dto.LoginRequest;
import com.aws.ws.infrastructure.inbound.enums.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User toDomain(LoginRequest user) {
        if (user == null) return null;
        return User.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(Roles.USER.getRoleName())
                .build();
    }
}
