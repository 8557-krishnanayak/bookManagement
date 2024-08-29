package com.godigit.bookmybook.model;

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
    @Column(name = "id")
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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wish_id")
    private WishListModel wishList;

    @PrePersist
    void preInsert() {
        if (this.role == null)
            this.role = "Customer";

        if (this.wishList == null)
            this.wishList = new WishListModel();
    }
}
