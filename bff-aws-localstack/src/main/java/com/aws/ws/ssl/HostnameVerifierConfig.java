package com.aws.ws.ssl;

import org.springframework.context.annotation.Configuration;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

@Configuration
public class HostnameVerifierConfig implements HostnameVerifier {

    @Override
    public boolean verify(String s, SSLSession sslSession) {
        return false;
    }

}
