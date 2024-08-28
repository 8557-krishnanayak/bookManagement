package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.dto.BookDTO;

import java.util.List;

public class CartConvertor {

    public static CartDto toDTO(CartModel cart){
        CartDto dto = new CartDto();

        List<BookDTO> bookDto = cart.getBook().stream().map(CartConvertor::toBookDto).toList();

        dto.setBook(bookDto);
        dto.setQuantity(cart.getQuantity());
        dto.setUser(cart.getUser());
        dto.setTotalPrice(cart.getTotalPrice());

        return dto;
    }

    public static CartModel toCartEntity(CartDto dto){
        CartModel cart = new CartModel();

        cart.setQuantity(dto.getQuantity());

        cart.setUser(dto.getUser());

        cart.setTotalPrice(dto.getTotalPrice());

        List<BookModel> book = dto.getBook().stream().map(CartConvertor::toBookEntity).toList();
        cart.setBook(book);

        return cart;
    }

    public static BookDTO toBookDto(BookModel book) {
        BookDTO dto = new BookDTO();

        dto.setBookName(book.getBookName());
        dto.setLogo(book.getLogo());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setQuantity(book.getQuantity());
        dto.setPrice(book.getPrice());
        dto.setAuthor(book.getAuthor());

        return dto;
    }


    public static BookModel toBookEntity(BookDTO Dto) {
        BookModel book = new BookModel();

        book.setBookName(Dto.getBookName());
        book.setLogo(Dto.getLogo());
        book.setPrice(Dto.getPrice());
        book.setDescription(Dto.getDescription());
        book.setQuantity(Dto.getQuantity());
        book.setPrice(Dto.getPrice());
        book.setAuthor(Dto.getAuthor());

        return book;
    }

}
