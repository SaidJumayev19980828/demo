package com.era.tofate.ws.exception;

public class AuthSocketException extends RuntimeException{
    public AuthSocketException(String message) {
        super(message);
    }

    public AuthSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
