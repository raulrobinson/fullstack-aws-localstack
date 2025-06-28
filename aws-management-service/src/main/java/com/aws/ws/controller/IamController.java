package com.aws.ws.controller;

import com.aws.ws.dto.IamUserDto;
import com.aws.ws.infrastructure.adapter.aws.IamAdapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "IAM", description = "AWS IAM Management")
public class IamController {

    private final IamAdapter iamService;

    @GetMapping("/aws/user")
    @Operation(description = "Get current IAM user details")
    public IamUserDto getCurrentUser() {
        return iamService.getCurrentUser();
    }
}
