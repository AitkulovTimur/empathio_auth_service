package com.val_pms.auth.service;

import com.val_pms.auth.exception.RefreshTokenExpiredException;
import com.val_pms.auth.exception.TokenRefreshingException;
import com.val_pms.auth.jwt.JwtUtils;
import com.val_pms.auth.model.RefreshAccessTokenDTO;
import com.val_pms.auth.model.RefreshToken;
import com.val_pms.auth.repository.RefreshTokenRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${val_pms.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    EntityManager entityManager;

    @Autowired
    RefreshTokenRepository refreshTokenRepository;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    JwtUtils jwtUtils;

    public ResponseEntity<RefreshAccessTokenDTO> refreshAccessTokenByRefresh(String refreshToken) {
        return refreshTokenRepository.findById(UUID.fromString(refreshToken))
                .map(this::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtUtils.generateJwtTokenFromUsername(user.getUsername());
                    return ResponseEntity.ok(RefreshAccessTokenDTO.builder().accessToken(token)
                            .refreshToken(refreshToken)
                            .build());
                })
                .orElseThrow(() -> new TokenRefreshingException(refreshToken));
    }

    @Transactional
    public RefreshToken createRefreshToken(UUID userId) {
        if (refreshTokenRepository.existsByUserId(userId)) {
            deleteByUserId(userId);
            entityManager.flush();
        }

        return refreshTokenRepository.save(RefreshToken.builder()
                .user(userEntityService.getUserById(userId))
                .expiryMoment(Instant.now().plusMillis(refreshTokenDurationMs))
                .build());
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryMoment().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenExpiredException();
        }

        return token;
    }

    @Transactional
    public int deleteByUserId(UUID userId) {
        return refreshTokenRepository.deleteByUser(userEntityService.getUserById(userId));
    }
}
