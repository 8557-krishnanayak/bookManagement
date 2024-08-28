package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.UserModel;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {

    private Long id;

    @NotEmpty(message = "firstname can't be blank")
    private String firstname;

    @NotEmpty(message = "lastname can't be blank")
    private String lastname;


    private LocalDate birthDate;

    @NotBlank(message = "password is required")
    private String password;

    @Email(message = "user need to give email")
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
