package com.aws.ws.domain.api;

import com.aws.ws.dto.BucketDto;

import java.util.List;

public interface S3AdapterPort {
    List<BucketDto> listBuckets();
}
