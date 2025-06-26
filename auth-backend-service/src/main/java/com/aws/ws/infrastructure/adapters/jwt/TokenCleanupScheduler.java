package com.aws.ws.infrastructure.adapters.jwt;

import com.aws.ws.infrastructure.adapters.persistence.UserPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCleanupScheduler {

    private final UserPersistenceAdapter tokenAdapter;

    // Ejecutar cada hora (puedes ajustar esto)
    //@Scheduled(cron = "0 0 * * * *") // cada hora en punto
    @Scheduled(cron = "*/30 * * * * *") // cada 30 segundos
    public void cleanExpiredTokens() {
        log.info("⏳ Starting scheduled cleanup of expired JWT tokens...");
        tokenAdapter.deactivateExpiredTokens()
                .doOnError(e -> log.error("❌ Error cleaning expired tokens: {}", e.getMessage()))
                .subscribe();
    }
}
