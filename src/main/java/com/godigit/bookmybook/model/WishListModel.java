package com.godigit.bookmybook.model;

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
    @OneToOne(cascade = CascadeType.ALL, targetEntity = UserModel.class)
    @JoinColumn(name = "user_id")
    private UserModel user;

    @Builder.Default
    @ManyToMany(cascade = CascadeType.ALL, targetEntity = BookModel.class)
    @JoinTable(
            name = "book_wishlist",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookModel> bookModelList = new ArrayList<>();
}
