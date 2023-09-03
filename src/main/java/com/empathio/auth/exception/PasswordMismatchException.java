package com.empathio.auth.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Passwords mismatch");
    }
}
