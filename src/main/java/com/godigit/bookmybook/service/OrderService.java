package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.*;
import com.godigit.bookmybook.exception.InvalidCustomerException;
import com.godigit.bookmybook.exception.ResourceNotFoundException;
import com.godigit.bookmybook.model.*;
import com.godigit.bookmybook.repository.OrderRepo;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataLocationNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    BookService bookService;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;


    public OrderModel placeOrder(AddressDTO addressDTO, long userId, String token) {
        UserModel userModel = userService.getUserModalById(userId);
        List<CartModel> cart_details = userModel.getCart();
        long price = 0;
        int quantity = 0;
        BookModel book = null;
        Address add = new Address(addressDTO);
        OrderModel order_save = null;
        for (CartModel cart : cart_details) {
            quantity += (int) cart.getQuantity();
            book = cart.getBook();
            bookService.changeBookQuantity(token, cart.getBook().getId(), ((int) cart.getBook().getQuantity() - quantity));
            price += cart.getTotalPrice() * cart.getQuantity();
            OrderModel orderModel = new OrderModel();
            orderModel.setBook(book);
            orderModel.setUser(userModel);
            orderModel.setAddress(add);
            orderModel.setQuantity(quantity);
            orderModel.setPrice(price);
            order_save = orderRepo.save(orderModel);
            quantity = 0;
        }
        cartService.removeByUserId(token);
        return order_save;
    }


    public OrderModel placeOrderByToken(String token, AddressDTO addressDTO) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("customer")) {
            throw new InvalidCustomerException("he/she is not a customer");
        }
        return placeOrder(addressDTO, decode.getId(), token);
    }


    public void cancelOrderById(String token, long orderId) {
        OrderModel orderModel = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order doesn't exists"));
        orderModel.setCancel(true);
        orderRepo.save(orderModel);
        BookModel book = orderModel.getBook();
        int quantity = orderModel.getQuantity();
        bookService.changeBookQuantity(token, book.getId(), (int) (book.getQuantity() + quantity));
    }


    public List<OrderModel> getAllOrders(String token, boolean cancel) {

        DataHolder decode = tokenUtility.decode(token);
        if (decode.getRole().equalsIgnoreCase("customer")) {
            throw new ResourceNotFoundException("no orders found");
        }

        return orderRepo.findByCancel(cancel);
    }


    public List<OrderModel> getAllOrdersForUser(String token) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("customer")) {
            throw new ResourceNotFoundException("no orders found");
        }
        return orderRepo.findByUserId(decode.getId());
    }

}
