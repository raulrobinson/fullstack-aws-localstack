package com.aws.ws.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String ID;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
}
