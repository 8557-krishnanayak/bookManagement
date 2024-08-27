package com.godigit.bookmybook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookModel {

    @Id
    @GeneratedValue
    @Column(name = "")
    private long id;

    private String bookName;
    private String author;
    private String description;

    @Column(name = "book_logo")
    private byte[] logo;

    private double price;
    private long quantity;
}
