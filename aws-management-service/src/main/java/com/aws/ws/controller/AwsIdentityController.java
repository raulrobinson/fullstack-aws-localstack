package com.aws.ws.controller;

import com.aws.ws.dto.AwsIdentityDto;
import com.aws.ws.service.AwsIdentityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws/identity")
@RequiredArgsConstructor
public class AwsIdentityController {

    private final AwsIdentityService awsIdentityService;

    @GetMapping
    public AwsIdentityDto getIdentity() {
        return awsIdentityService.getCallerIdentity();
    }
}
