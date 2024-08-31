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
        List<BookModel> books = cart_details.stream().map(CartModel::getBook).toList();
        long price=0;
        int quantity=0;
        int totalquantity=0;

        for(CartModel cart:cart_details){
            quantity+=cart.getQuantity();
            totalquantity+=quantity;
            bookService.changeBookQuantity(token,cart.getBook().getId(),  ((int) cart.getBook().getQuantity()-quantity));
            price+= cart.getTotalPrice() * cart.getQuantity();
            quantity=0;
        }
        Address add = new Address(addressDTO);
        OrderModel orderModel = new OrderModel();
        orderModel.setBooks(books);
        orderModel.setUser(userModel);
        orderModel.setAddress(add);
        orderModel.setQuantity(totalquantity);
        orderModel.setPrice(price);
        orderRepo.save(orderModel);
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
        for (BookModel book : orderModel.getBooks()) {
            int quantity=orderModel.getQuantity();
            long bookId = book.getId();
            System.out.println(book.getQuantity());
            bookService.changeBookQuantity(token, bookId, (int) (book.getQuantity()+quantity));
        }

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
