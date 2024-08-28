package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

        private UserModel user;

        @Builder.Default
        private List<BookDTO> book = new ArrayList<>();

//        @NotBlank(message = "Quantity should not be blank")
        private long quantity;
        private long totalPrice;
    }


