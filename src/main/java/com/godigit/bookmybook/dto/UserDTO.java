package com.godigit.bookmybook.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;

    private String password;
    private String email;
    private String role;

    private LocalDate registeredDate;
    private LocalDate updateDate;
}
