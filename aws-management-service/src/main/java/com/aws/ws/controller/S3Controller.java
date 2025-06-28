package com.aws.ws.controller;

import com.aws.ws.dto.BucketDto;
import com.aws.ws.infrastructure.adapter.aws.S3Adapter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws/s3")
@RequiredArgsConstructor
@Tag(name = "S3 Management", description = "Operations related to AWS S3")
public class S3Controller {

    private final S3Adapter s3Service;

    @GetMapping("/buckets")
    @Operation(description = "List all S3 buckets")
    public List<BucketDto> listBuckets() {
        return s3Service.listBuckets();
    }
}
