package com.val_pms.auth.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "username must not be null")
    private String username;
    @NotBlank(message = "password must not be null")
    private String password;
}
