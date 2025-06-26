package com.aws.ws.infrastructure.adapters.jwt;

import com.aws.ws.domain.api.JwtAdapterPort;
import com.aws.ws.domain.model.Token;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAdapter implements JwtAdapterPort {

    public static final ZoneId ZONE_ID = ZoneId.of("America/Bogota");

    @Value("${jwt.secret}")
    private String SECRET_KEY; // Clave secreta para firmar el JWT, inyectada desde las propiedades de la aplicación

    @Value("${jwt.expiration-time-sec}")
    private long EXPIRATION_TIME_SEC;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Mono<Token> generateToken(String email, List<String> roles) {
        ZonedDateTime now = ZonedDateTime.now(ZONE_ID);
        ZonedDateTime expiresAt = now.plusSeconds(EXPIRATION_TIME_SEC);

        log.info("Generating JWT for email: {}, roles: {}, expires at: {}", email, roles, expiresAt);

        String jwt = Jwts.builder()
                .setSubject(email)
                .claim("roles", roles)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(expiresAt.toInstant()))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()), SignatureAlgorithm.HS256)
                .compact();

        return Mono.just(Token.builder()
                .jwt(jwt)
                .issuedAt(now)
                .expiresAt(expiresAt)
                .build());
    }

    @Override
    public Mono<Boolean> validateJwt(String jwt) {
        try {
            Claims claims = validateToken(jwt);

            boolean isExpired = claims.getExpiration().before(new Date());
            return Mono.just(!isExpired);
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("❌ Invalid JWT: {}", e.getMessage());
            return Mono.just(false);
        }
    }

    public Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return validateToken(token).getSubject();
    }

    public List<String> extractRoles(String token) {
        Object roles = validateToken(token).get("roles");
        return objectMapper.convertValue(roles, new TypeReference<>() {});
    }

}

