package com.empathio.auth.repository;

import com.empathio.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(nativeQuery = true,
            value = """
                    SELECT id
                    FROM users
                    WHERE username = :username
                    """)
    Optional<String> returnIdByUsername(String username);
}
