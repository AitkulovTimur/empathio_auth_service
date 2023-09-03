package com.empathio.auth.exception;

import static java.lang.String.format;

public class TokenRefreshingException extends RuntimeException {
    public TokenRefreshingException(String refreshToken) {
        super(format("Refresh token: %s wasn't found. Sign in again, please", refreshToken));
    }
}
