package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.LoginDto;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDto) {
        userDto.setRole("Admin");
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        return new ResponseEntity<>(userService.loginService(email, password), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAll(@RequestHeader String token) {
        return new ResponseEntity<>(userService.getAllUser(token), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getAll(@RequestHeader String token, @PathVariable Long id) {
        return new ResponseEntity<>(userService.getIdByToken(token, id), HttpStatus.OK);
    }
}
