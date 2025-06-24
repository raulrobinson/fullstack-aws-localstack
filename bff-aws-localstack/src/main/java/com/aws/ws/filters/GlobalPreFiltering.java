package com.aws.ws.filters;

import com.aws.ws.config.AllowedOriginsProperties;
import com.aws.ws.config.CustomHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Configuration
@RequiredArgsConstructor
public class GlobalPreFiltering implements GlobalFilter {

    private final AllowedOriginsProperties allowedOriginsProperties;
    private final CustomHeader customHeader;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Global Pre Filter executed");
        return chain.filter(exchange);
    }

    @Bean
    public GlobalFilter customGlobalPreFilter() {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            String origin = exchange.getRequest().getHeaders().getOrigin();
            String path = exchange.getRequest().getPath().toString();

            log.info("🌐 Origin: {}", origin);
            log.info("✅ Allowed: {}", allowedOriginsProperties.getOrigins());

            if ((origin == null && !path.startsWith("/actuator")) ||
                    (origin != null && !allowedOriginsProperties.getOrigins().contains(origin))) {

                log.warn("❌ Origin blocked: {}", origin);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            String headerName = customHeader.getHeader().getName();
            String expectedValue = customHeader.getHeader().getValue();

            String actualValue = request.getHeaders().getFirst(headerName);
            log.info("🔍 Checking header {} = {}", headerName, actualValue);

            if (actualValue == null || !actualValue.equals(expectedValue)) {
                log.warn("❌ Invalid or missing {} header", headerName);
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                return response.setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
