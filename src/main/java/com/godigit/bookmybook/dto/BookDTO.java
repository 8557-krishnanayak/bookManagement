package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.converstion.FeedbackConverter;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.ImageModel;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.model.WishListModel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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


    private String description ;

    private ImageModel logo;

    private double price;
    private long quantity;

    private List<WishListModel> wishList;
    @Builder.Default
    private List<FeedBackDTO> feedBack = new ArrayList<>();

    public BookDTO(BookModel book) {
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.description = book.getDescription();
        this.logo = book.getLogo();
        this.price = book.getPrice();
        this.quantity = book.getQuantity();
       // this.feedBack=book.getFeedBack().stream().map(FeedbackConverter::toDTO).toList();

    }
}
