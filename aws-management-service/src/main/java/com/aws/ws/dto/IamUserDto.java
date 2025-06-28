package com.aws.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "IAM User Data Transfer Object")
public class IamUserDto {

    @Schema(description = "User Name", example = "john_doe")
    private String userName;

    @Schema(description = "User ID", example = "AID1234567890EXAMPLE")
    private String userId;

    @Schema(description = "ARN (Amazon Resource Name)", example = "arn:aws:iam::123456789012:user/john_doe")
    private String arn;

    @Schema(description = "User Creation Date", example = "2024-10-01T12:00:00Z")
    private String createDate;
}
