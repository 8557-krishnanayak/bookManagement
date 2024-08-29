package com.godigit.bookmybook.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        this("you're Unauthorized!");
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
