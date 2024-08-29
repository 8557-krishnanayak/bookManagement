package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.WishListConvertor;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.model.WishListModel;
import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.repository.WishListRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishListService {
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    WishListRepository wishListRepository;

    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

    @Autowired
    TokenUtility tokenUtility;

    public WishListDTO createWishlist(String token, Long book_id) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long user_id = dataHolder.getId();

        BookModel bookModel = bookService.getBookByID(book_id, token);
        WishListDTO addedWishList = addWishlist(user_id, bookModel);
        return addedWishList;
    }

    public WishListDTO addWishlist(Long user_id, BookModel bookModel) {

        UserModel user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        WishListModel wishListModel = new WishListModel(); // new

        wishListModel.setUser(user);
        List<BookModel> bookModelList = new ArrayList<>();
        bookModelList.add(bookModel);
        wishListModel.setBookModelList(bookModelList);
        WishListModel savedWishList = wishListRepository.save(wishListModel);
        return WishListConvertor.toDTO(savedWishList);
    }

    public  WishListDTO getWishList(String token){
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId(); // user id

        WishListModel wishListModel = wishListRepository
                .findById(userId) //
                .orElse(new WishListModel());
        return WishListConvertor.toDTO(wishListModel);
    }

//    public String removeWishList(Long wishlist_id){
//
//
//    }

//    public String removeWishListByUser(String token,Long user_id){
//        DataHolder dataHolder = tokenUtility.decode(token);
//        Long userid = dataHolder.getId();
//        WishListModel delete_book = deleteById(userid);
//    }
}
