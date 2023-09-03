package com.empathio.auth.exception;

public class RefreshTokenExpiredException extends RuntimeException {
    public RefreshTokenExpiredException() {
        super("Refresh token was expired. Please, make a new signing request");
    }
}
