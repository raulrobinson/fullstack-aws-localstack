package com.aws.ws.controller;

import com.aws.ws.service.AwsStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AwsStatusController {

    private final AwsStatusService awsStatusService;

    @GetMapping("/aws/status")
    public Map<String, Object> getStatus() {
        return awsStatusService.getAwsStatus();
    }
}
