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


    /**
     * Purpose: This API (Application Programming Interface) is created for adding book to the cart.
     * @param token This is the authentication token of the user
     * @param book_id The ID of the book which is to be added to the cart
     * @return returns the updated cart details.
     */
    @PostMapping("/addBook")
    public ResponseEntity<CartDto> addToCart(@RequestHeader String token, @RequestParam long book_id) {
        return new ResponseEntity<>(cart_service.addToCart(token,book_id),HttpStatus.CREATED);
    }


    /**
     * Purpose: This API(Application Programming Interface) is created fo removing an item from the cart
     * @param cart_id This is the ID of the cart item to be removed
     * @return Returns a message indicating the result of removing operation
     */
    @DeleteMapping("/deleteitem/{cart_id}")
    public ResponseEntity<String> removeItem(@PathVariable long cart_id) {
       return  new ResponseEntity<>(cart_service.removeItem(cart_id),HttpStatus.OK);
    }


    /**
     * Purpose: This API(Application Programming Interface) is created for removng an item from the acrt by using the user ID
     * @param token This is the authentication token of the user
     * @return Returns a message indicating the result of removing operation
     */
    @DeleteMapping("/removebyid")
    public ResponseEntity<?> removeByUserId(@RequestHeader String token){
       return new ResponseEntity<>( cart_service.removeByUserId(token),HttpStatus.OK);
    }


    /**
     * Purpose: This API(Application Programming Interface) is created for increasing the quantity of the book product
     * @param token This is the authentication token of the user
     * @param cart_id This is the ID of the cart item which is to be updated
     * @param quantity This is the quantity which is to be updated for the book in the cart
     * @return Returns a message indicating the cart updated
     */
    @PutMapping("/increasequantity/{cart_id}/{quantity}")
    public ResponseEntity<?> increaseQuantity(@RequestHeader String token,@PathVariable long cart_id,@PathVariable long quantity){
        return new ResponseEntity<>(cart_service.increaseQuantity(token,cart_id,quantity),HttpStatus.OK);

    }


    /**
     * Purpose: This API(Application Programming Interface) is created for decreasing the quantity of the book product
     * @param token This is the authentication token of the user
     * @param cart_id This is the ID of the cart item which is to be updated
     * @param quantity This is the quantity which is to be updated for the book in the cart
     * @return Returns a message indicating the cart updated
     */


    @PutMapping("/deacreasequantity/{cart_id}/{quantity}")
    public ResponseEntity<?> decreaseQuantity(@RequestHeader String token,@PathVariable long cart_id,@PathVariable long quantity){
        return new ResponseEntity<>(cart_service.decreaseQuantity(token,cart_id,quantity),HttpStatus.OK);

    }

    /**
     * Purpose: This API(Application Programming Interface) is created to retrieve cart items for a particular user
     * @param token This is the authentication token of the user
     * @return Returns a list of all cart items for the user
     */
    @GetMapping("/getitemsforuser")
    public ResponseEntity<List<CartModel>> getAllCartItemsForUser(@RequestHeader String token){
        return new ResponseEntity<>(cart_service.getAllCartItemsForUser(token),HttpStatus.OK);
    }


    /**
     * Purpose: This API(Application Programming Interface) is created to get all cart items
     * @return : Returns a list of all cart items for the user
     */
    @GetMapping("/cartitems")
    public ResponseEntity<?> getAllCartItems(@RequestHeader String token){
        return new ResponseEntity<>(cart_service.getAllCartItems(token),HttpStatus.OK);
    }
}
