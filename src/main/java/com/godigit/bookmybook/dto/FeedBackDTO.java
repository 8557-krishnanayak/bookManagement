package com.godigit.bookmybook.dto;


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
public class FeedBackDTO {
    private long id;
    private Long rating;
    private BookDTO book;
    private String Comments;

    private UserDTO user;
}


