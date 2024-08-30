package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.dto.BookDTO;

import java.util.List;

public class CartConvertor {

    public static CartDto toDTO(CartModel cart){

        if(cart==null)
            return null;

        CartDto dto = new CartDto();

        dto.setId(cart.getId());
        dto.setBook(cart.getBook());
        dto.setQuantity(cart.getQuantity());
        dto.setTotalPrice(cart.getTotalPrice());

        return dto;
    }

    public static CartModel toCartEntity(CartDto dto){
        if(dto==null){
            return null;
        }
        CartModel cart = new CartModel();
        cart.setId(dto.getId());

        cart.setQuantity(dto.getQuantity());
        cart.setTotalPrice(dto.getTotalPrice());
        cart.setBook(dto.getBook());

        return cart;
    }

    public static BookDTO toBookDto(BookModel book) {
        if(book == null)
            return null;
        BookDTO dto = new BookDTO();

        dto.setId(book.getId());
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
        if(Dto == null)
            return null;
        BookModel book = new BookModel();

        book.setId(Dto.getId());
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
