package com.aws.ws.service;

import com.aws.ws.dto.AwsIdentityDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sts.StsClient;

@Service
@RequiredArgsConstructor
public class AwsIdentityService {

    private final StsClient stsClient;

    public AwsIdentityDto getCallerIdentity() {
        var identity = stsClient.getCallerIdentity();
        String arn = identity.arn();
        String displayName = arn.endsWith(":root") ? "root" : extractUserFromArn(arn);

        return new AwsIdentityDto(
                identity.userId(),
                identity.account(),
                arn,
                displayName
        );
    }

    private String extractUserFromArn(String arn) {
        if (arn.contains("user/")) {
            return arn.substring(arn.indexOf("user/") + 5);
        } else if (arn.contains("assumed-role/")) {
            String[] parts = arn.split("/");
            return parts.length > 1 ? parts[1] : "unknown-role";
        } else {
            return "unknown";
        }
    }

}
