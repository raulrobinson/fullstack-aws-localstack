package com.aws.ws.infrastructure.adapter.aws;

import com.aws.ws.domain.api.S3AdapterPort;
import com.aws.ws.dto.BucketDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class S3Adapter implements S3AdapterPort {

    private final S3Client s3Client;

    @Override
    public List<BucketDto> listBuckets() {
        return s3Client.listBuckets().buckets().stream()
                .map(bucket -> new BucketDto(bucket.name(), bucket.creationDate()))
                .collect(Collectors.toList());
    }
}
