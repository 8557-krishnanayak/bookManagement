package com.godigit.bookmybook.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Min(value = 1,message = "hey dude didn't you shop before the value starting for rating is from one")
    @Max(value = 5,message = "value should not exceed 5")
    private Long rating;
    private BookDTO book;
    private String Comments;

    private UserDTO user;
}


