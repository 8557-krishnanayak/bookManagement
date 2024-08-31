package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.*;
import com.godigit.bookmybook.model.*;
import com.godigit.bookmybook.repository.OrderRepo;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepo orderRepo;

    @Autowired
    BookService  bookService;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;


    public OrderModel placeOrder(AddressDTO addressDTO, long userId, String token) {
        UserModel userModel = userService.getUserModalById(userId);

        List<CartModel> cart_details = userModel.getCart();
        System.err.println(cart_details);// all cart of user
        System.err.println(addressDTO);
        List<BookModel> books = cart_details.stream().map(CartModel::getBook).toList();



        long price=0;
        int quantity=0;

        for(CartModel cart:cart_details){
            quantity+=cart.getQuantity();
            bookService.changeBookQuantity(token,cart.getBook().getId(),  ((int) cart.getBook().getQuantity()-quantity));
            price+= cart.getTotalPrice() * cart.getQuantity();

        }
        System.err.println(price);
        System.err.println(quantity);
        System.err.println(books);

        Address add = new Address(addressDTO);

        OrderModel orderModel = new OrderModel();
        orderModel.setBooks(books);
        orderModel.setUser(userModel);
        orderModel.setAddress(add);
        orderModel.setQuantity(quantity);
        orderModel.setPrice(price);
        orderRepo.save(orderModel);
//        cart_details.stream().map(c->{
//            bookService.changeBookQuantity(token,c.getBook().getId(), (int)(c.getBook().getQuantity()-c.getQuantity()));
//            return null;
//        });
        cartService.removeByUserId(token);




        return orderModel;
    }




    public OrderModel placeOrderByToken(String token, AddressDTO addressDTO) {
        DataHolder decode = tokenUtility.decode(token);
        if (!decode.getRole().equalsIgnoreCase("customer")) {
            throw new RuntimeException("he/she is not a customer");
        }
        return placeOrder(addressDTO,decode.getId(),token);

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

//    order-id,
}
