package com.godigit.bookmybook.exception;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException() {
        this("Resource Already Exist");
    }

    public ResourceAlreadyExistException(String message) {
        super(message);
    }
}
