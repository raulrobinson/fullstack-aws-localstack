package com.aws.ws.controller;

import com.aws.ws.dto.BucketDto;
import com.aws.ws.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aws/s3")
@RequiredArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    @GetMapping("/buckets")
    public List<BucketDto> listBuckets() {
        return s3Service.listBuckets();
    }
}
