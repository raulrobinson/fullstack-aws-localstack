package com.aws.ws.service;

import com.aws.ws.config.AwsProperties;
import com.aws.ws.models.ReceiveRequest;
import com.aws.ws.models.SendRequest;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Service
@EnableConfigurationProperties(AwsProperties.class)
public class SqsService {

    private final SqsClient sqs;

    public SqsService(AwsProperties aws) {
        this.sqs = SqsClient.builder()
                .endpointOverride(URI.create(aws.getUrl()))
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                )
                .build();
    }

    public Mono<List<String>> listQueues() {
        return Mono.fromCallable(() -> sqs.listQueues().queueUrls());
    }

    public Mono<Void> sendMessage(SendRequest req) {
        return Mono.fromRunnable(() ->
                sqs.sendMessage(SendMessageRequest.builder()
                        .queueUrl(req.getQueueUrl())
                        .messageBody(req.getMessageBody())
                        .build())
        );
    }

    public Mono<List<Map<String, String>>> receiveMessages(ReceiveRequest req) {
        return Mono.fromCallable(() -> {
            List<Message> msgs = sqs.receiveMessage(ReceiveMessageRequest.builder()
                    .queueUrl(req.getQueueUrl())
                    .maxNumberOfMessages(5)
                    .build()).messages();
            return msgs.stream()
                    .map(m -> Map.of(
                            "MessageId", m.messageId(),
                            "Body", m.body(),
                            "ReceiptHandle", m.receiptHandle()
                    ))
                    .toList();
        });
    }
}

