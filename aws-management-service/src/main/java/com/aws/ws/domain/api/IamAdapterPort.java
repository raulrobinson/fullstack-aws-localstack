package com.aws.ws.domain.api;

import com.aws.ws.dto.IamUserDto;

public interface IamAdapterPort {
    IamUserDto getCurrentUser();
}
