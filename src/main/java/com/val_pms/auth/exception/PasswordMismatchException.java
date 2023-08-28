package com.val_pms.auth.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Passwords mismatch");
    }
}
