package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.dto.UserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user")
    private List<FeedBackModel> feedbacks;

    @PrePersist
    void preInsert() {
        if (this.role == null)
            this.role = "Customer";

        if (this.wishList == null)
            this.wishList = new WishListModel();

        if (this.feedbacks == null) {
            this.feedbacks = new ArrayList<>();
        }
    }
}
