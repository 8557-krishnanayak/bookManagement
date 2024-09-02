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
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    /**
     * Purpose: This Api is to create for registration new admin user into system
     *
     * @param userDto Response coming form the frontend will go to handle in this controller class as this request.
     *                so we are mapping this request into UserDTO POJO class by the @Request and then give the DTO to
     *                the service to process.
     * @return return the RequestEntity holding the value of the User details object along with Status code
     */
    @PostMapping("/registration")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO userDto) {
        userDto.setRole("Admin");
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
     * Purpose: This API is to retrieve all users.
     *
     * @param token The token provided in the request header for authorization. Only Admin token is valid
     * @return Returns a ResponseEntity holding the list of all users along with the status code.
     */
    @GetMapping("/users")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(userService.getAllUser(token), HttpStatus.OK);
    }

    /**
     * Purpose: This API is to retrieve a specific user by their ID.
     *
     * @param token The token provided in the request header for authentication and authorization.
     * @param id    The ID of the user to be retrieved.
     * @return Returns a ResponseEntity holding the user details object along with the status code.
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getAll(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        return new ResponseEntity<>(userService.getIdByToken(token, id), HttpStatus.OK);
    }
}
