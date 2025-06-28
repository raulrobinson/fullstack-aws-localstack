package com.aws.ws.infrastructure.adapter.aws;

import com.aws.ws.domain.api.SqsAdapterPort;
import com.aws.ws.dto.SqsQueueDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SqsAdapter implements SqsAdapterPort {

    private final SqsClient sqsClient;

    @Override
    public List<SqsQueueDto> listQueues() {
        return sqsClient.listQueues().queueUrls().stream()
                .map(SqsQueueDto::new)
                .collect(Collectors.toList());
    }
}
