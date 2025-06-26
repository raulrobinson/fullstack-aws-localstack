package com.aws.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AuthBackendServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthBackendServiceApplication.class, args);
    }

}
