package com.aws.ws.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IamUserDto {
    private String userName;
    private String userId;
    private String arn;
    private String createDate;
}
