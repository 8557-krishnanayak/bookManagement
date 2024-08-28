package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.BookModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long book_id;

    @NotBlank(message = "Book name should not be blank")
    private String bookName;

    @NotBlank(message = "Enter a valid author name")
    private String author;

    @NotBlank(message = "Give a description about the book with min 200 characters")
    @Min(value = 200)
    private String description;

    private byte[] logo;

    private double price;
    private long quantity;

    public BookDTO(BookModel book) {
        this.book_id = book.getId();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.logo = book.getLogo();
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
    }
}
