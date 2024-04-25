package com.md.account.exception;

public class InvalidJwtException extends RuntimeException {
    public InvalidJwtException(String invalidToken) {
        super(invalidToken);
    }
}
