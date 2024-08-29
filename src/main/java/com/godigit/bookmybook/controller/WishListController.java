package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.WishListModel;
import com.godigit.bookmybook.service.BookService;
import com.godigit.bookmybook.service.WishListService;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
public class WishListController {

    @Autowired
    WishListService wishListService;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    BookService bookService;

    @PostMapping
    public ResponseEntity<?> createWishList(@RequestHeader String token, @RequestParam Long book_id) {
        // Call the createWishlist method from the WishListService
        WishListDTO addedWishList = wishListService.createWishlist(token, book_id);
        return new ResponseEntity<>(addedWishList, HttpStatus.CREATED);
    }

    @GetMapping("/show/user")
    public  ResponseEntity<?> getWishList(@RequestHeader String token){
        WishListDTO gotWishList=wishListService.getWishList(token);
      return new ResponseEntity<>(gotWishList,HttpStatus.FOUND);
    }
//    @DeleteMapping("/Delete")
//      public ResponseEntity<?> removeWishList(@RequestParam Long wishlist_id){
//
//    }
//
//      @DeleteMapping("/Delete/user")
//        public ResponseEntity<?>removeWishListByUser(@RequestHeader String token,@RequestParam Long user_id){
//
//
//      }

}
