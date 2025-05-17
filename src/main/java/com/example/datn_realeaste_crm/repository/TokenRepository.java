package com.example.datn_realeaste_crm.repository;
import com.example.datn_realeaste_crm.entity.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findByToken(String token);

    Optional<Token> findByTokenAndRevokedTrue(String token);

    @Query("SELECT t FROM Token t WHERE t.user.userId = ?1 AND t.revoked = false AND t.tokenType = ?2")
    List<Token> findAllValidTokensByUser(Integer userId, String tokenType);

    List<Token> findAllByExpirationDateBeforeAndRevokedFalse(LocalDateTime dateTime);

    @Modifying
    @Transactional
    @Query("UPDATE Token t SET t.revoked = true WHERE t.user.userId = :userId")
    int revokeAllUserTokens(@Param("userId") Integer userId);
}