package com.godigit.bookmybook.model;

import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
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

    @Builder.Default
    private String role = "Customer";

    @CreationTimestamp
    private LocalDate registeredDate;

    @UpdateTimestamp
    private LocalDate updateDate;

    @OneToOne
    @Builder.Default
    private CartModel cart = new CartModel();

    @PrePersist
    void preInsert() {
        if (this.role == null)
            this.role = "Customer";
    }
}
