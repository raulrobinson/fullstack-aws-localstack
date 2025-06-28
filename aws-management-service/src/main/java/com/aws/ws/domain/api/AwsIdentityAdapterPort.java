package com.aws.ws.domain.api;

import com.aws.ws.dto.AwsIdentityDto;

public interface AwsIdentityAdapterPort {
    AwsIdentityDto getCallerIdentity();
}
