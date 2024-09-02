package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.converstion.FeedbackConverter;
import com.godigit.bookmybook.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"wishList"})
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

    @OneToMany(mappedBy = "book")
    private List<WishListModel> wishList;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<FeedBackModel> feedBack;



    public BookModel(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.author = bookDTO.getAuthor();
        this.description = bookDTO.getDescription();
        this.logo = bookDTO.getLogo();
        this.price = bookDTO.getPrice();
        this.quantity = bookDTO.getQuantity();
      // this.feedBack=bookDTO.getFeedBack().stream().map(FeedbackConverter::toEntity).toList();

    }
}
