package com.aws.ws.service;

import com.aws.ws.common.exception.TechnicalException;
import com.aws.ws.config.AwsProperties;
import com.aws.ws.exception.TechnicalMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ScanRequest;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@EnableConfigurationProperties(AwsProperties.class)
public class DynamoService {

    private final DynamoDbClient dynamo;

    public DynamoService(AwsProperties aws) {
        this.dynamo = DynamoDbClient.builder()
                .endpointOverride(URI.create(aws.getUrl()))
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials
                        .create(aws.getAccessKeyId(), aws.getSecretAccessKey())))
                .build();
    }

    // ✅ Listar todas las tablas
    public Mono<List<String>> listTables() {
        return Mono.fromCallable(() -> dynamo.listTables().tableNames())
                .doOnSuccess(tables -> log.info("Tables found: {}", tables))
                .doOnError(e -> log.error("Error listing tables", e)) // <-- efecto secundario
                .onErrorMap(e -> new TechnicalException(TechnicalMessage.ADAPTER_ERROR, e)); // <-- transforma el error
    }

    // ✅ Escanear todos los ítems de una tabla
    public Mono<List<Map<String, Object>>> listItems(String tableName) {
        return Mono.fromCallable(() -> {
                    List<Map<String, AttributeValue>> items = dynamo.scan(
                            ScanRequest.builder().tableName(tableName).build()
                    ).items();

                    return items.stream()
                            .map(this::convertItem)
                            .toList();
                })
                .doOnSuccess(items -> log.info("Items found for table '{}': {}", tableName, items))
                .doOnError(e -> log.error("Error fetching items from table '{}'", tableName, e))
                .onErrorMap(e -> new TechnicalException(TechnicalMessage.ADAPTER_ERROR, e));
    }

    // ✅ Insertar item (clave 'id' obligatoria de tipo string)
    public Mono<Void> putItem(String tableName, Map<String, Object> jsonItem) {
        Map<String, AttributeValue> item = jsonItem.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> AttributeValue.builder().s(entry.getValue().toString()).build()
                ));

        return Mono.fromRunnable(() -> dynamo.putItem(PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build()));
    }

    private Map<String, Object> convertItem(Map<String, AttributeValue> item) {
        return item.entrySet().stream()
                .collect(java.util.stream.Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            AttributeValue val = entry.getValue();
                            if (val.s() != null) return val.s();
                            if (val.n() != null) return val.n();
                            if (val.bool() != null) return val.bool();
                            if (val.hasL()) return val.l().stream().map(this::getValue).toList();
                            if (val.hasM()) return convertItem(val.m());
                            return val.toString();
                        }
                ));
    }

    private Object getValue(AttributeValue val) {
        if (val.s() != null) return val.s();
        if (val.n() != null) return val.n();
        if (val.bool() != null) return val.bool();
        if (val.hasM()) return convertItem(val.m());
        if (val.hasL()) return val.l().stream().map(this::getValue).toList();
        return null;
    }
}
