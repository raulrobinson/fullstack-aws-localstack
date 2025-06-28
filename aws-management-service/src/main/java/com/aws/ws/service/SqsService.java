package com.aws.ws.service;

import com.aws.ws.dto.SqsQueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SqsService {

    private final SqsClient sqsClient;

    public List<SqsQueueDto> listQueues() {
        return sqsClient.listQueues().queueUrls().stream()
                .map(SqsQueueDto::new)
                .collect(Collectors.toList());
    }
}
