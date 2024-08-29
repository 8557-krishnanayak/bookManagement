package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.model.WishListModel;

import java.util.List;

public class WishListConvertor {
    public static WishListDTO toDTO(WishListModel wishList) {
        WishListDTO listDTO = new WishListDTO();

        List<BookDTO> bookList = wishList.getBookModelList().stream().map(WishListConvertor::toBookDto).toList();
        listDTO.setBookDTOList(bookList);
        listDTO.setId(wishList.getId());

        listDTO.setUserId(wishList.getUserId());

        return listDTO;
    }

    public static WishListModel toEntity(WishListDTO listDTO) {
        WishListModel wishList = new WishListModel();

        List<BookModel> bookModelList = listDTO.getBookDTOList()
                .stream().map(WishListConvertor::toBookEntity).toList();


        wishList.setBookModelList(bookModelList);
        wishList.setId(listDTO.getId());
        Long userId = listDTO.getUserId();
        wishList.setUserId(userId);

        return wishList;
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
