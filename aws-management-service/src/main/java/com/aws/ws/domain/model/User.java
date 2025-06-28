package com.aws.ws.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("firstname")
    private String userName;

    @JsonProperty("lastname")
    private String userLastName;

    @JsonProperty("email")
    private String userEmail;

    @JsonProperty("password")
    private String userPassword;

    @JsonProperty("role")
    private String userRole;
}
