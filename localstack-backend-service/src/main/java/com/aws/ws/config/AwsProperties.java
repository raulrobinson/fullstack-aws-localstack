package com.aws.ws.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "aws.localstack")
public class AwsProperties {
    private String url;
    private String region;
    private String accessKeyId;
    private String secretAccessKey;
}
