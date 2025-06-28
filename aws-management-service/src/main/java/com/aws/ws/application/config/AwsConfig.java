package com.aws.ws.application.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sts.StsClient;

import java.net.URI;

@Configuration
@EnableConfigurationProperties(AwsProperties.class)
public class AwsConfig {

    @Bean
    public StsClient stsClient(AwsProperties aws) {
        var builder = StsClient.builder()
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                );

        if (aws.getLocalstackEnabled()) {
            builder.endpointOverride(URI.create(aws.getLocalStackUrl()));
        }

        return builder.build();
    }

    @Bean
    public IamClient iamClient(AwsProperties aws) {
        var builder = IamClient.builder()
                .region(Region.AWS_GLOBAL) // IAM solo funciona en región global
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                );

        if (aws.getLocalstackEnabled()) {
            builder.endpointOverride(URI.create(aws.getLocalStackUrl()));
        }

        return builder.build();
    }


    @Bean
    public S3Client s3Client(AwsProperties aws) {
        S3ClientBuilder builder = S3Client.builder()
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                );

        if (aws.getLocalstackEnabled()) {
            builder.endpointOverride(URI.create(aws.getLocalStackUrl()));
        }

        return builder.build();
    }

    @Bean
    public DynamoDbClient dynamoDbClient(AwsProperties aws) {
        var builder = DynamoDbClient.builder()
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                );

        if (aws.getLocalstackEnabled()) {
            builder.endpointOverride(URI.create(aws.getLocalStackUrl()));
        }

        return builder.build();
    }


    @Bean
    public SqsClient sqsClient(AwsProperties aws) {
        var builder = SqsClient.builder()
                .region(Region.of(aws.getRegion()))
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(aws.getAccessKeyId(), aws.getSecretAccessKey())
                        )
                );

        if (aws.getLocalstackEnabled()) {
            builder.endpointOverride(URI.create(aws.getLocalStackUrl()));
        }

        return builder.build();
    }
}

