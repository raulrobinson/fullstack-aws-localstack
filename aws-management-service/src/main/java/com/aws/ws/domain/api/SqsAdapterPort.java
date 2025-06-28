package com.aws.ws.domain.api;

import com.aws.ws.dto.SqsQueueDto;

import java.util.List;

public interface SqsAdapterPort {
    List<SqsQueueDto> listQueues();
}
