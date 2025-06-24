package com.aws.ws.ssl;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

@Slf4j
@Configuration
public class SSLCertConfig {

    /**
     * Disable SSL certificate validation (for development purposes only).
     */
    @Bean
    @SneakyThrows
    public Boolean disableSSLValidation() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                        log.warn("⚠️ checkClientTrusted called: authType = {}", authType);
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                        log.warn("⚠️ checkServerTrusted called: authType = {}", authType);
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                }
        };

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

        HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifierConfig());

        log.warn("⚠️ SSL Certificate validation is disabled. DO NOT USE IN PRODUCTION!");

        return true;
    }
}
