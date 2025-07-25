package com.aws.ws.service;

import com.aws.ws.config.AwsProperties;
import com.aws.ws.dto.LambdaFunctionDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Service
@EnableConfigurationProperties(AwsProperties.class)
public class LambdaService {

    private final LambdaClient lambdaClient;

    public LambdaService(AwsProperties aws) {
        this.lambdaClient = LambdaClient.builder()
                .endpointOverride(URI.create(aws.getUrl())) // e.g., http://localhost:4566 for LocalStack
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                ))
                .build();
    }

    /**
     * Invoca una función Lambda de forma síncrona.
     * @param functionName Nombre de la función Lambda
     * @param payload JSON como String
     * @return respuesta de la Lambda como String
     */
    public String invokeSync(String functionName, String payload) {
        try {
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(SdkBytes.fromString(payload, StandardCharsets.UTF_8))
                    .invocationType(InvocationType.REQUEST_RESPONSE)
                    .build();

            InvokeResponse response = lambdaClient.invoke(request);
            return response.payload().asUtf8String();
        } catch (LambdaException e) {
            log.error("Error al invocar Lambda: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Invoca una función Lambda de forma asíncrona (event).
     * @param functionName Nombre de la función Lambda
     * @param payload JSON como String
     */
    public void invokeAsync(String functionName, String payload) {
        try {
            InvokeRequest request = InvokeRequest.builder()
                    .functionName(functionName)
                    .payload(SdkBytes.fromString(payload, StandardCharsets.UTF_8))
                    .invocationType(InvocationType.EVENT)
                    .build();

            lambdaClient.invoke(request);
            log.info("Lambda {} invocada asíncronamente", functionName);
        } catch (LambdaException e) {
            log.error("Error al invocar Lambda async: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Lista todas las funciones Lambda disponibles.
     * @return lista de configuraciones de funciones Lambda
     */
    public List<LambdaFunctionDto> listLambdas() {
        ListFunctionsResponse response = lambdaClient.listFunctions();
        return response.functions().stream()
                .map(func -> LambdaFunctionDto.builder()
                        .functionName(func.functionName())
                        .runtime(func.runtimeAsString())
                        .handler(func.handler())
                        .build())
                .toList();
    }
}
