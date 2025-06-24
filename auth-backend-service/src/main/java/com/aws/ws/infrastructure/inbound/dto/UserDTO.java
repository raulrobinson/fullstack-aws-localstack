package com.aws.ws.infrastructure.inbound.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Data Transfer Object for User", title = "UserDTO")
public class UserDTO {
    @Schema(description = "Email for the user", example = "rasysbox@hotmail.com")
    private String email;
    @Schema(description = "First name of the user", example = "Raul")
    private String firstName;
    @Schema(description = "Last name of the user", example = "Bolivar")
    private String lastName;
    @Schema(description = "Password for the user", example = "password123")
    private String password;
    @Schema(description = "Role of the user", example = "USER")
    private String role;
}
