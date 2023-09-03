package com.empathio.auth.model;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class UserDTO {
    UUID id;

    String username;

    String email;

    Set<String> roles;

    Boolean blocked;

    OffsetDateTime expirationDateTime;

    String firstName;

    String lastName;

    int age;
}
