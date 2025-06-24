package com.aws.ws;

import com.aws.ws.config.AwsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AwsProperties.class)
public class AwsProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsProxyApplication.class, args);
    }

}
