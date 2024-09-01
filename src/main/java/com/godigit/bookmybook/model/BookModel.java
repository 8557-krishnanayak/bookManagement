package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.dto.BookDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.web.bind.annotation.RequestPart;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"orders"})
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private long id;

    private String bookName;
    private String author;
    private String description;

    @JoinColumn(name = "book_logo")
    @OneToOne(cascade = CascadeType.ALL)
    private ImageModel logo;

    private double price;
    private long quantity;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<OrderModel> orders;


    public BookModel(BookDTO bookDTO) {
        this.bookName = bookDTO.getBookName();
        this.author = bookDTO.getAuthor();
        this.description = bookDTO.getDescription();
        this.logo = bookDTO.getLogo();
        this.price = bookDTO.getPrice();
        this.quantity = bookDTO.getQuantity();
    }
}
