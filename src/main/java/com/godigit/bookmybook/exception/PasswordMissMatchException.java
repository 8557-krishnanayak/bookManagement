package com.godigit.bookmybook.exception;

public class PasswordMissMatchException extends RuntimeException{
    public PasswordMissMatchException() {
        this("Passwords do not match. Please try again.");
    }

    public PasswordMissMatchException(String message) {
        super(message);
    }
}
