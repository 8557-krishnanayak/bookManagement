package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.model.WishListModel;

import java.util.List;

public class WishListConvertor {
    public static WishListDTO toDTO(WishListModel wishList) {
        WishListDTO listDTO = new WishListDTO();
        listDTO.setId(wishList.getId());

        BookModel book = wishList.getBook();
        BookDTO bookDto = WishListConvertor.toBookDto(book);
        listDTO.setBook(bookDto);

//        UserModel user = wishList.getUser();
//        UserDTO userDto = UserConverter.toDTO(user);
//        listDTO.setUser(userDto);

        return listDTO;
    }

    public static WishListModel toEntity(WishListDTO listDTO) {
        WishListModel wishList = new WishListModel();
        listDTO.setId(listDTO.getId());

        BookDTO book = listDTO.getBook();
        BookModel bookModel = WishListConvertor.toBookEntity(book);
        wishList.setBook(bookModel);
//
//        UserDTO user = listDTO.getUser();
//        UserModel userModel = UserConverter.toEntity(user);
//        wishList.setUser(userModel);

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


        List<FeedBackDTO> list = book.getFeedBack().stream().map(FeedbackConverter::toDTO).toList();
        dto.setFeedBack(list);

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

        List<FeedBackModel> list = Dto.getFeedBack().stream().map(FeedbackConverter::toEntity).toList();
        book.setFeedBack(list);

        return book;
    }

}