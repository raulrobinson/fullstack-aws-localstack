package com.aws.ws.controller;

import com.aws.ws.infrastructure.adapter.aws.AwsStatusAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "AWS Status", description = "Endpoints to check AWS service status")
public class AwsStatusController {

    private final AwsStatusAdapter awsStatusService;

    @GetMapping("/aws/status")
    @Operation(description = "Get the current status of AWS services")
    public Map<String, Object> getStatus() {
        return awsStatusService.getAwsStatus();
    }
}
