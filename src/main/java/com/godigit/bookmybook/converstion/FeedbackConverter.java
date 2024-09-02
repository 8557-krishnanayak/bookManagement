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
        return DTO;
    }

    public static FeedBackModel toEntity(FeedBackDTO DTO) {
        FeedBackModel feedBack = new FeedBackModel();

        feedBack.setComments(DTO.getComments());
        feedBack.setId(DTO.getId());
        feedBack.setRating(DTO.getRating());
        return feedBack;
    }
}