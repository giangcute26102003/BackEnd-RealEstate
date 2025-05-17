package com.example.datn_realeaste_crm.config;


import com.example.datn_realeaste_crm.entity.Token;
import com.example.datn_realeaste_crm.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    
    private final TokenRepository tokenRepository;
    
    // Run every day at 1:00 AM
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional
    public void cleanupExpiredTokens() {
        LocalDateTime now = LocalDateTime.now();
        List<Token> expiredTokens = tokenRepository.findAllByExpirationDateBeforeAndRevokedFalse(now);
        
        if (!expiredTokens.isEmpty()) {
            expiredTokens.forEach(token -> token.setExpired(true));
            tokenRepository.saveAll(expiredTokens);
            
            log.info("Cleaned up {} expired tokens", expiredTokens.size());
        }
    }
}