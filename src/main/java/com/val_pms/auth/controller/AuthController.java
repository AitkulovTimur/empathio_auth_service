package com.val_pms.auth.controller;

import com.val_pms.auth.model.LoginRequest;
import com.val_pms.auth.model.RefreshAccessTokenDTO;
import com.val_pms.auth.model.RefreshTokenRequest;
import com.val_pms.auth.model.UserRegistrationRequest;
import com.val_pms.auth.model.UserDTO;
import com.val_pms.auth.service.AuthenticationService;
import com.val_pms.auth.service.RefreshTokenService;
import com.val_pms.auth.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    UserEntityService userEntityService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/sign_in")
    public ResponseEntity<UserDTO> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticateUser(loginRequest);
    }

    @PostMapping("/sign_up")
    public ResponseEntity<UserDTO> signupUser(@Valid @RequestBody UserRegistrationRequest registrationRequest) {
        return userEntityService.registerUser(registrationRequest);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity<RefreshAccessTokenDTO> refreshAccessToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenService.refreshAccessTokenByRefresh(refreshTokenRequest.getRefreshToken());
    }
}
