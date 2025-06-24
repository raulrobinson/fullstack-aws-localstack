package com.aws.ws.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "custom")
public class CustomHeader {
    private Header header;

    @Data
    public static class Header {
        private String name;
        private String value;
    }
}
