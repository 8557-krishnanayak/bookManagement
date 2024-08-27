package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.UserModel;
import jakarta.persistence.PrePersist;
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


    public UserDTO(UserModel user) {
        this(user.getId(),
                user.getFirstname(),
                user.getLastname(),
                user.getBirthDate(),
                user.getPassword(),
                user.getEmail(),
                user.getRole(),
                user.getRegisteredDate(),
                user.getUpdateDate()
                );


    }
}
