package com.aws.ws.models;

import lombok.Data;

@Data
public class SendRequest {
    private String queueUrl;
    private String messageBody;
}
