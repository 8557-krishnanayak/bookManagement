package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.dto.BookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "WishList")
public class WishListModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long userId;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.PERSIST, targetEntity = BookModel.class)
    @JoinTable(
            name = "book_wishlist",
            joinColumns = @JoinColumn(name = "wish_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    @JsonIgnore
    private List<BookModel> bookModelList = new ArrayList<>();

//@Builder.Default
//@OneToMany(cascade = CascadeType.PERSIST, targetEntity = BookModel.class)
//@JoinTable(
//        name = "book_wishlist",
//        joinColumns = @JoinColumn(name = "wish_id"),
//        inverseJoinColumns = @JoinColumn(name = "book_id")
//)
//@JsonIgnore
//private List<BookModel> bookModelList = new ArrayList<>();
}
