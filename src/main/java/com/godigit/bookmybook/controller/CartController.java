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
    public ResponseEntity<CartDto> addToCart(@RequestHeader String token, @RequestParam long book_id) {
        return new ResponseEntity<>(cart_service.addToCart(token,book_id),HttpStatus.CREATED);
    }


    @DeleteMapping("/deleteitem/{cart_id}")
    public ResponseEntity<String> removeItem(@PathVariable long cart_id) {
       return  new ResponseEntity<>(cart_service.removeItem(cart_id),HttpStatus.CREATED);
    }

    @DeleteMapping("/removebyid")
    public ResponseEntity<?> removeByUserId(@RequestHeader String token){
       return new ResponseEntity<>( cart_service.removeByUserId(token),HttpStatus.CREATED);
    }


    @PutMapping("/updatequantity/{cart_id}/{quantity}")
    public void updateQuantity(@RequestHeader String token,@PathVariable long cart_id,@PathVariable long quantity){
        cart_service.updateQuantity(token,cart_id,quantity);

    }


    @GetMapping("/getitemsforuser")
    public List<CartModel> getAllCartItemsForUser(@RequestHeader String token){
        return cart_service.getAllCartItemsForUser(token);
    }


    @GetMapping("/cartitems")
    public List<CartModel> getAllCartItems(){
        return cart_service.getAllCartItems();
    }
}
