package com.godigit.bookmybook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public ResponseEntity<?> resourceAlreadyExistException(ResourceAlreadyExistException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordMissMatchException.class)
    public ResponseEntity<?> passwordMissMatchException(PasswordMissMatchException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> unauthorizedException(UnauthorizedException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<?> forbiddenException(ForbiddenException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> methodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> runtimeException(RuntimeException e) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> ioException(IOException e)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> nullPointerException(NullPointerException e)
    {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", e.getMessage());
        errors.put("exception", e.getClass().toString());
        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
}
