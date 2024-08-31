package com.godigit.bookmybook.converstion;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.model.UserModel;

import java.util.List;

public class FeedbackConverter {
    public static FeedBackDTO toDTO(FeedBackModel feedBack) {
        FeedBackDTO DTO = new FeedBackDTO();
        DTO.setComments(feedBack.getComments());
        DTO.setId(feedBack.getId());
        DTO.setRating(feedBack.getRating());
//        BookModel book = feedBack.getBook();
//        BookDTO bookDto = WishListConvertor.toBookDto(book);
//        UserModel user = feedBack.getUser();
//        UserDTO userDto = UserConverter.toDTO(user);
//
//        DTO.setUser(userDto);
//        DTO.setBook(bookDto);


//        List<UserDTO> user = feedBack.getUserModel().stream().map(UserConverter::toDTO).toList();
//        DTO.setUser(user);
//        DTO.setBook(bookDto);


        return DTO;
    }

    public static FeedBackModel toEntity(FeedBackDTO DTO) {
        FeedBackModel feedBack = new FeedBackModel();

        feedBack.setComments(DTO.getComments());
        feedBack.setId(DTO.getId());
        feedBack.setRating(DTO.getRating());

//        BookDTO bookDTO = DTO.getBook();
//        BookModel bookModel = WishListConvertor.toBookEntity(bookDTO);
//        feedBack.setBook(bookModel);
//
//        UserDTO user = DTO.getUser();
//        UserModel userModel = UserConverter.toEntity(user);
//
//        feedBack.setUser(userModel);

//        feedBack.setUserModel(user);

        return feedBack;
    }
}