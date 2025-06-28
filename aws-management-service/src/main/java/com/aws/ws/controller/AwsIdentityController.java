package com.aws.ws.controller;

import com.aws.ws.dto.AwsIdentityDto;
import com.aws.ws.infrastructure.adapter.aws.AwsIdentityAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws/identity")
@RequiredArgsConstructor
@Tag(name = "AWS Identity", description = "Controller for AWS Identity operations")
public class AwsIdentityController {

    private final AwsIdentityAdapter awsIdentityService;

    @GetMapping
    @Operation(description = "Get AWS Caller Identity")
    public AwsIdentityDto getIdentity() {
        return awsIdentityService.getCallerIdentity();
    }
}
