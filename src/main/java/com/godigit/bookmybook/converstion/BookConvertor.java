package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.FeedBackModel;

import java.util.List;

public class BookConvertor {
    public static BookDTO toDTO(BookModel book) {
        BookDTO dto = new BookDTO();

        if (book == null) return dto;

        dto.setBookName(book.getBookName());
        dto.setLogo(book.getLogo());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setQuantity(book.getQuantity());
        dto.setPrice(book.getPrice());
        dto.setAuthor(book.getAuthor());

        List<FeedBackDTO> list = book.getFeedBack().stream().map(FeedbackConverter::toDTO).toList();
        dto.setFeedBack(list);

        return dto;
    }


    public static BookModel toEntity(BookDTO Dto) {
        BookModel book = new BookModel();
        if (Dto == null) return book;

        book.setBookName(Dto.getBookName());
        book.setLogo(Dto.getLogo());
        book.setPrice(Dto.getPrice());
        book.setDescription(Dto.getDescription());
        book.setQuantity(Dto.getQuantity());
        book.setPrice(Dto.getPrice());
        book.setAuthor(Dto.getAuthor());

        List<FeedBackModel> list = Dto.getFeedBack().stream().map(FeedbackConverter::toEntity).toList();
        book.setFeedBack(list);

        return book;
    }
}
