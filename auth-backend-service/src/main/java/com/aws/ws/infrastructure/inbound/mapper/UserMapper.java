package com.aws.ws.infrastructure.inbound.mapper;

import com.aws.ws.domain.model.User;
import com.aws.ws.infrastructure.inbound.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserDTO userDTO) {
        if (userDTO == null) return null;
        return User.builder()
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .password(userDTO.getPassword())
                .role(userDTO.getRole())
                .build();
    }
}
