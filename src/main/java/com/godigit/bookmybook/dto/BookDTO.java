package com.godigit.bookmybook.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class BookDTO {

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
}
