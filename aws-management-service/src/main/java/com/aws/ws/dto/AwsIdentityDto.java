package com.aws.ws.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "AWS Identity Data Transfer Object")
public class AwsIdentityDto {

    @Schema(description = "User ID", example = "AID1234567890EXAMPLE")
    private String userId;

    @Schema(description = "Account ID", example = "123456789012")
    private String account;

    @Schema(description = "ARN (Amazon Resource Name)", example = "arn:aws:iam::123456789012:user/JohnDoe")
    private String arn;

    @Schema(description = "User Name", example = "JohnDoe")
    private String displayName;
}
