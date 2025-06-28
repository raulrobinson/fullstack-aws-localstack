package com.aws.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AwsIdentityDto {
    private String userId;
    private String account;
    private String arn;
    private String displayName;
}
