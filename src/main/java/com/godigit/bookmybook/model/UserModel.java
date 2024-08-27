package com.godigit.bookmybook.model;

import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@AllArgsConstructor
@NamedEntityGraph
@Data
@Builder
@Entity
@Table(name = "users")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private LocalDate birthDate;


    private String password;
    private String email;
    private String role;

    @CreationTimestamp
    private LocalDate registeredDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    public UserModel(UserDTO user) {
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
