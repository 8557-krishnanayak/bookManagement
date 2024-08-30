package com.godigit.bookmybook.dto;

import com.godigit.bookmybook.model.Address;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.OrderModel;
import com.godigit.bookmybook.model.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private long id;
    private LocalDate orderDate;
    private double price;
    private int quantity;

    private Address address;
    private UserModel user;
    private List<String> books;
    private boolean cancel;

}
