package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;



@NoArgsConstructor
@Getter
@Setter
@Data
public class CartDto {


        private  Long id;
        private BookModel book;

//        @NotBlank(message = "Quantity should not be blank")
        private long quantity=1;
        private long totalPrice;

        public CartDto(CartDto cart){
                this.book=cart.getBook();
                this.quantity=cart.getQuantity();
                this.totalPrice= cart.getTotalPrice();
                this.id= cart.getId();
        }


    }


