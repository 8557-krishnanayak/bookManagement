package com.godigit.bookmybook.model;

import com.godigit.bookmybook.dto.BookDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    private String bookName;
    private String author;
    private String description;

    @Column(name = "book_logo")
    private byte[] logo;

    private double price;
    private long quantity;

    @ManyToMany(mappedBy = "bookModelList")
    private List<WishListModel> wishList;

    public BookModel(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.author = bookDTO.getAuthor();
        this.description = bookDTO.getDescription();
        this.logo = bookDTO.getLogo();
        this.price = bookDTO.getPrice();
        this.quantity = bookDTO.getQuantity();
    }
}
