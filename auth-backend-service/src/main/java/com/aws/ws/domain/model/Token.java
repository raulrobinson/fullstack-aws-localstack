package com.aws.ws.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Token {
    private String tokenId;      // puede ser UUID
    private String userId;
    private String jwt;
    private ZonedDateTime issuedAt;
    private ZonedDateTime expiresAt;
    private boolean active;      // marcarlo false en logout
}
