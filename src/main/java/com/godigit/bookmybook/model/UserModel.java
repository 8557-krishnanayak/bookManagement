package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.godigit.bookmybook.dto.CartDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "users")
    @JsonManagedReference(value = "cartref")
    private List<CartModel> cart;

    @OneToMany(mappedBy = "user", fetch=FetchType.LAZY)
    private List<OrderModel> orders;


    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonManagedReference(value = "wish_ref")
    @JsonIgnore
    private List<WishListModel> wishList;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    @JsonIgnore
    private List<FeedBackModel> feedbacks;

    @PrePersist
    void preInsert() {
        if (this.role == null)
            this.role = "Customer";

        if (this.wishList == null)
            this.wishList = new ArrayList<>();

        if (this.feedbacks == null) {
            this.feedbacks = new ArrayList<>();
        }

        if (this.orders == null) {
            this.orders = new ArrayList<>();
        }
    }
}
