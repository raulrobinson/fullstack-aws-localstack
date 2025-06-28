package com.aws.ws.controller;

import com.aws.ws.dto.SqsQueueDto;
import com.aws.ws.infrastructure.adapter.aws.SqsAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws/sqs")
@RequiredArgsConstructor
@Tag(name = "SQS Management", description = "Endpoints for managing AWS SQS queues")
public class SqsController {

    private final SqsAdapter sqsService;

    @GetMapping("/queues")
    @Operation(description = "List all SQS queues")
    public List<SqsQueueDto> listQueues() {
        return sqsService.listQueues();
    }
}
