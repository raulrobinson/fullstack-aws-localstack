package com.aws.ws.infrastructure.adapter.aws;

import com.aws.ws.domain.api.AwsStatusAdapterPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AwsStatusAdapter implements AwsStatusAdapterPort {

    private final S3Client s3Client;
    private final DynamoDbClient dynamoDbClient;
    private final SqsClient sqsClient;

    @Override
    public Map<String, Object> getAwsStatus() {
        Map<String, Object> status = new HashMap<>();

        try {
            var buckets = s3Client.listBuckets().buckets();
            status.put("s3", "OK (" + buckets.size() + " buckets)");
        } catch (Exception e) {
            status.put("s3", "ERROR: " + e.getMessage());
        }

        try {
            var tables = dynamoDbClient.listTables().tableNames();
            status.put("dynamodb", "OK (" + tables.size() + " tables)");
        } catch (Exception e) {
            status.put("dynamodb", "ERROR: " + e.getMessage());
        }

        try {
            var queues = sqsClient.listQueues().queueUrls();
            status.put("sqs", "OK (" + queues.size() + " queues)");
        } catch (Exception e) {
            status.put("sqs", "ERROR: " + e.getMessage());
        }

        return status;
    }
}
