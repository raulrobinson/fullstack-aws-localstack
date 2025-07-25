package com.aws.ws.handler;

import com.aws.ws.common.handler.GlobalErrorHandler;
import com.aws.ws.dto.LambdaFunctionDto;
import com.aws.ws.service.LambdaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.lambda.model.FunctionConfiguration;

import java.util.List;

@Component
@RequiredArgsConstructor
@Tag(name = "Lambdas", description = "Lambdas Management")
public class LambdaHandler {

    private final LambdaService lambdaService;
    private final GlobalErrorHandler globalErrorHandler;

    public Mono<ServerResponse> invokeLambda(ServerRequest request) {
        String name = request.queryParam("name").orElse("default");
        return request.bodyToMono(String.class)
                .flatMap(body -> {
                    String result = lambdaService.invokeSync(name, body);
                    return ServerResponse.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(result);
                });
    }

    public Mono<ServerResponse> invokeLambdaAsync(ServerRequest request) {
        String name = request.queryParam("name").orElse("default");
        return request.bodyToMono(String.class)
                .flatMap(body -> {
                    lambdaService.invokeAsync(name, body);
                    return ServerResponse.accepted()
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue("{\"status\": \"Lambda invocation accepted\"}");
                });
    }

    public Mono<ServerResponse> listLambdas(ServerRequest request) {
        List<LambdaFunctionDto> lambdas = lambdaService.listLambdas();
        return ServerResponse.ok().bodyValue(lambdas);
    }
}
