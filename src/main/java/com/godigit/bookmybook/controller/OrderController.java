package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.AddressDTO;
import com.godigit.bookmybook.dto.OrderDTO;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.OrderModel;
import com.godigit.bookmybook.service.OrderService;
import com.godigit.bookmybook.service.UserService;
import jakarta.persistence.criteria.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    /**
     * Purpose: This API (Application Programming Interface) is created for placing an order.
     * @param token This is the authentication token of the user.
     * @param addressDTO The data transfer object containing the address details for the order.
     * @return returns the details of the placed order.
     */

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestHeader String token,@RequestBody AddressDTO addressDTO){
        return new ResponseEntity<>(orderService.placeOrderByToken(token, addressDTO), HttpStatus.ACCEPTED);

    }


    /**
     * Purpose: This API (Application Programming Interface) is created for canceling an existing order.
     * @param token This is the authentication token of the user.
     * @param orderId The ID of the order which is to be canceled.
     * @return void.
     */


    @PutMapping("/cancelOrder/{orderId}")
    public void cancelOrder(@RequestHeader String token, @PathVariable long orderId){
         orderService.cancelOrderById(token,orderId);

    }

    /**
     * Purpose: This API (Application Programming Interface) is created for retrieving all orders.
     * @param token This is the authentication token of the user.
     * @param cancel This is a boolean flag to filter orders based on their cancellation status. Default value is false.
     * @return returns a list of all orders.
     */

    @GetMapping("/getAllOrders")
    public List<OrderModel> getAllOrders(@RequestHeader String token, @RequestParam(defaultValue = "false") boolean cancel){
        return orderService.getAllOrders(token, cancel);
    }

    /**
     * Purpose: This API (Application Programming Interface) is created for retrieving all orders for a specific user.
     * @param token This is the authentication token of the user.
     * @return returns a ResponseEntity containing a list of all orders for the user. If no orders are found, it returns a 404 Not Found status.
     */

    @GetMapping("/getAllordersForUser")
    public ResponseEntity<List<OrderModel>> getAllOrdersForUser(@RequestHeader String token){
        List<OrderModel> orders=orderService.getAllOrdersForUser(token);
        if(orders.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);
    }
}
