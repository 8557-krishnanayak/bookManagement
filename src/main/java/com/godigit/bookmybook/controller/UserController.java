package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.LoginDto;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDto) {
        userDto.setRole("Customer");
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        return new ResponseEntity<>(userService.loginService(email, password), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> update(@RequestHeader String token, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateByToken(token, userDTO), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestHeader String token) {
        return new ResponseEntity<>(userService.deleteByToken(token), HttpStatus.OK);
    }
}
