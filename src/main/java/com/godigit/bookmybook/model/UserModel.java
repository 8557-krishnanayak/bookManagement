package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = "cart")
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

    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "users")
    @JsonManagedReference(value = "cartref")
    private List<CartModel> cart;

    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
    private List<OrderModel> orders;


}
