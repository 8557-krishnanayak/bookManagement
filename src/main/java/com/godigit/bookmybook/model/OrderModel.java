package com.godigit.bookmybook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.godigit.bookmybook.dto.AddressDTO;
import com.godigit.bookmybook.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString(exclude = {"user"})
public class OrderModel {
    @Id
    @GeneratedValue
    private long id;

    @CreationTimestamp
    private LocalDate orderDate;

    private double price;
    private int quantity;

    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserModel user;

    @ManyToMany
    @JoinTable(
            name = "order_books",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id")
    )
    private List<BookModel> books;


    @PrePersist
    void pre(){
        if(this.books==null)
            this.books=new ArrayList<>();
    }

    private boolean cancel = false;

    public OrderModel(OrderDTO orderDto) {
        this.price = orderDto.getPrice();
        this.quantity = orderDto.getQuantity();
        this.cancel = orderDto.isCancel();
        this.address = Address.builder()
                .type(orderDto.getAddress().getType())
                .city(orderDto.getAddress().getCity())
                .state(orderDto.getAddress().getState())
                .pinCode(orderDto.getAddress().getPinCode()).build();
    }

}
