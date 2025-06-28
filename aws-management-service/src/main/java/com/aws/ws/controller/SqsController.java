package com.aws.ws.controller;

import com.aws.ws.dto.SqsQueueDto;
import com.aws.ws.service.SqsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws/sqs")
@RequiredArgsConstructor
public class SqsController {

    private final SqsService sqsService;

    @GetMapping("/queues")
    public List<SqsQueueDto> listQueues() {
        return sqsService.listQueues();
    }
}
