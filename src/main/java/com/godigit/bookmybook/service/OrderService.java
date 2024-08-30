package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.OrderDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.Address;
import com.godigit.bookmybook.model.OrderModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.OrderRepo;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    UserService userService;


    public OrderModel placeOrder(OrderDTO orderDTO, long userId) {
        UserDTO userById = userService.getUserById(userId);
        UserModel u = UserConverter.toEntity(userById);

//        we retrieve the data of cart model
//        u.getCart(); List<C
//        art>


        OrderModel orderModel = new OrderModel(orderDTO);
        System.out.println(orderModel);
        Address add = orderModel.getAddress();
        orderModel.setUser(u);
        orderModel.setAddress(add);



        orderRepo.save(orderModel);
        return (orderModel);

    }


    public OrderModel placeOrderByToken(OrderDTO orderDTO, String token) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("customer")) {
            throw new RuntimeException("he/she is not a customer");
        }
        return placeOrder(orderDTO, decode.getId());

    }

    public void cancelOrderById(String token, long orderId) {
        OrderModel orderModel=orderRepo.findById(orderId).orElseThrow(()->new RuntimeException("Order doesn't exists"));
        orderModel.setCancel(true);
        orderRepo.save(orderModel);
    }


    public List<OrderModel> getAllOrders(String token, boolean cancel) {

        DataHolder decode = tokenUtility.decode(token);
        if (decode.getRole().equalsIgnoreCase("customer")) {
            throw new RuntimeException("no orders found");
        }

        return orderRepo.findByCancel(cancel);

    }


    public List<OrderModel> getAllOrdersForUser(String token) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("customer")) {
            throw new RuntimeException("no orders found");
        }

        return orderRepo.findByUserId(decode.getId());


    }
}
