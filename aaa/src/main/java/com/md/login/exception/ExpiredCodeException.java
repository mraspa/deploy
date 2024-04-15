package com.md.login.exception;

public class ExpiredCodeException extends Exception{
    public ExpiredCodeException(String message) {
        super(message);
    }
}
