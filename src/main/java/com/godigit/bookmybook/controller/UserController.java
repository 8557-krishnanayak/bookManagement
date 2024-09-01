package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.LoginDto;
import com.godigit.bookmybook.dto.Response.ApiResponse;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * Purpose: This API is to register a new customer user into the system.
     *
     * @param userDto Response coming from the frontend will be handled in this controller class as this request.
     *                The request is mapped into the UserDTO POJO class by the @RequestBody annotation and then given to
     *                the service to process.
     * @return Returns a ResponseEntity holding the value of the User details object along with the status code.
     */
    @PostMapping("/registration")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDto) {
        userDto.setRole("Customer");
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    /**
     * Purpose: This API is to handle the login request for users.
     *
     * @param loginDto Response coming from the frontend will be handled in this controller class as this request.
     *                 The request is mapped into the LoginDto POJO class by the @RequestBody annotation and then given to
     *                 the service to process.
     * @return Returns a ResponseEntity holding the value of the token data along with the status code.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        String email = loginDto.getEmail();
        String password = loginDto.getPassword();
        Map<String, String> map = new HashMap<>();
        map.put("token", userService.loginService(email, password));

        ApiResponse loginToken = ApiResponse.builder()
                .result(map).success(true).message("Login Token").build();

        return new ResponseEntity<>(loginToken, HttpStatus.OK);
    }


    /**
     * Purpose: This API is to update user details based on the provided token.
     *
     * @param token   The token provided in the request header for authentication and authorization.
     * @param userDTO Response coming from the frontend will be handled in this controller class as this request.
     *                The request is mapped into the UserDTO POJO class by the @RequestBody annotation and then given to
     *                the service to process update method.
     * @return Returns a ResponseEntity holding the updated User details object along with the status code.
     */
    @PutMapping
    public ResponseEntity<?> update(@RequestHeader String token, @Valid @RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateByToken(token, userDTO), HttpStatus.OK);
    }

    /**
     * Purpose: This API is to delete a user which is based on the provided token.
     *
     * @param token The token provided in the request header for authentication and authorization.
     * @return Returns a ResponseEntity holding the deletion confirmation message along with the status code.
     */
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestHeader String token) {
        return new ResponseEntity<>(userService.deleteByToken(token), HttpStatus.OK);
    }
}
