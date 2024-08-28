package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cart")
public class CartModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cart_id;

    @OneToOne
    @JsonIgnore
    private UserModel user;

    @ManyToMany
    @JoinTable(
            name = "Product_cart",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )

    @Builder.Default
    private List<BookModel> book = new ArrayList<>();

    private long quantity=1;
    private long totalPrice;


}
