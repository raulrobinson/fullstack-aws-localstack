package com.aws.ws.controller;

import com.aws.ws.dto.IamUserDto;
import com.aws.ws.service.IamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IamController {

    private final IamService iamService;

    @GetMapping("/aws/user")
    public IamUserDto getCurrentUser() {
        return iamService.getCurrentUser();
    }
}
