package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.UserModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishListDTO {

    private long id;

    private Long userId;

    @Builder.Default
    private List<BookDTO> bookDTOList = new ArrayList<>();

}
