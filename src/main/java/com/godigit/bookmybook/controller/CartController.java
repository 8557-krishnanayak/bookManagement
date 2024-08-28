package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    CartService cart_service;

    @PostMapping("/addBook")
    public ResponseEntity<CartModel> addToCart(@RequestHeader String token, @RequestParam long book_id){
       return new ResponseEntity<>( cart_service.addToCart(token,book_id), HttpStatus.CREATED);
    }
}
//
//    /**
//     * Purpose: This API(Application Programming Interface) is created for removing an item from the cart using cart id.
//     * @param
//     * @return It does'nt return anything, but in case if the cart id is invalid then it throws an Exception i.e DataNotFoundException.
//     */
//
//    @DeleteMapping("/deleteitem/{cart_id}")
//    public void removeItem(@RequestParam long cart_id){
//        cart_service.removeItem(cart_id);
//    }
//
//    @DeleteMapping("/removebyid/{user_id}")
//    public void removeByUserId(@RequestParam long user_id){
//        cart_service.removeByUserId(user_id);
//    }
//
//    @PutMapping("/updatequantity/{cart_id}")
//    public void updateQuantity(@RequestParam UserDTO user,@PathVariable long cart_id,@RequestParam long quantity){
//        cart_service.updateQuantity(user,cart_id,quantity);
//
//    }
//
//    /**
//     * Purpose:This API(Application Programming Interface) is created for retrieving all cart items for a specific user.
//     * @param userId
//     * @return: It returns a list which stores all the cart items for a specific user.
//     */
//
//    @GetMapping("/getitemsforuser/{userId}")
//    public List<CartModel> getAllCartItemsForUser(@PathVariable long userId){
//        return cart_service.getAllCartItemsForUser(userId);
//    }
//
//
//    /**
//     * Purpose: This API(Application Programming Interface) is created for retrieving all the cart items.
//     *
//     * @return: It returns a list which stores all the items which are in the cart.
//     */
//
//    @GetMapping("/cartitems")
//    public List<CartModel> getAllCartItems(){
//        return cart_service.getAllCartItems();
//    }
//}
