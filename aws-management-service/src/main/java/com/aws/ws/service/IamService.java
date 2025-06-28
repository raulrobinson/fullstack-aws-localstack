package com.aws.ws.service;

import com.aws.ws.dto.IamUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.GetUserResponse;

@Service
@RequiredArgsConstructor
public class IamService {

    private final IamClient iamClient;

    public IamUserDto getCurrentUser() {
        GetUserResponse userResponse = iamClient.getUser();
        var user = userResponse.user();
        return new IamUserDto(
                user.userName(),
                user.userId(),
                user.arn(),
                user.createDate().toString()
        );
    }
}
