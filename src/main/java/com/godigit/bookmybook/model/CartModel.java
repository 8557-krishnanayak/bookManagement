package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
    private Long id;

//    @ManyToOne
//    private UserModel user;
//
//
//    @ManyToOne
//    private BookModel book;

    private long quantity;
    private long totalPrice;


    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "cartref")
    private UserModel users;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id")
    private BookModel book;



}
