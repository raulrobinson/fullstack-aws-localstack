package com.aws.ws.infrastructure.adapter.aws;

import com.aws.ws.domain.api.IamAdapterPort;
import com.aws.ws.dto.IamUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.GetUserResponse;

@Service
@RequiredArgsConstructor
public class IamAdapter implements IamAdapterPort {

    private final IamClient iamClient;

    @Override
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
