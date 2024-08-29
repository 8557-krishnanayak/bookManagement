package com.godigit.bookmybook.model;

import com.godigit.bookmybook.dto.OrderDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
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
    private UserModel user;

    @ElementCollection
    @CollectionTable(name = "order_book", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "book_data")
    private List<String> books;


    private boolean cancel = false;

    public OrderModel(OrderDTO orderDto) {
        this.price = orderDto.getPrice();
        this.quantity = orderDto.getQuantity();
        this.cancel = orderDto.isCancel();
        this.address = orderDto.getAddress();
    }

}
