package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.ImageModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private long id;

    @NotBlank(message = "Book name should not be blank")
    private String bookName;

    @NotBlank(message = "Enter a valid author name")
    private String author;

    @NotBlank(message = "Give a description about the book with min 200 characters")
    private String description;

    private ImageModel logo;

    private double price;
    private long quantity;

    public BookDTO(BookModel book) {
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.logo = book.getLogo();
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
    }
}
