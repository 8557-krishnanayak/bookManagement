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
     * Purpose: this API(application programming interface) is created for
     * placing an order
     * @param
     * **/

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestHeader String token,@RequestBody AddressDTO addressDTO){
        return new ResponseEntity<>(orderService.placeOrderByToken(token, addressDTO), HttpStatus.ACCEPTED);

    }

    /**
     * cancel order by using order id**/

    @PutMapping("/cancelOrder/{orderId}")
    public void cancelOrder(@RequestHeader String token, @PathVariable long orderId){
         orderService.cancelOrderById(token,orderId);

    }

    /**
     * getAllOrders by using admin token
     * **/

    @GetMapping("/getAllOrders")
    public List<OrderModel> getAllOrders(@RequestHeader String token, @RequestParam(defaultValue = "false") boolean cancel){
        return orderService.getAllOrders(token, cancel);
    }

    /**
     * getAllOrdersForUser**/

    @GetMapping("/getAllordersForUser")
    public ResponseEntity<List<OrderModel>> getAllOrdersForUser(@RequestHeader String token){
        List<OrderModel> orders=orderService.getAllOrdersForUser(token);
        if(orders.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orders);

    }


}
