package com.aws.ws.application.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {
    private Boolean localstackEnabled;
    private String localStackUrl;
    private String region;
    private String accessKeyId;
    private String secretAccessKey;
}
