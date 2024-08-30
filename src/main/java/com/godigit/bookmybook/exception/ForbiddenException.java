package com.godigit.bookmybook.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        this("You do not have permission to access this resource.");
    }

    public ForbiddenException(String message) {
        super(message);
    }
}
