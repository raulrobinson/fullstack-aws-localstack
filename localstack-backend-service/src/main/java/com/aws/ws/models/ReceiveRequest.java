package com.aws.ws.models;

import lombok.Data;

@Data
public class ReceiveRequest {
    private String queueUrl;
}
