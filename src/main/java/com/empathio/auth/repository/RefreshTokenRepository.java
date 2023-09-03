package com.empathio.auth.repository;

import com.empathio.auth.model.RefreshToken;
import com.empathio.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    @Modifying
    int deleteByUser(User user);


    boolean existsByUserId(UUID userId);
}
