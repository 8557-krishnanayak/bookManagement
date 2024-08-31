package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.WishListConvertor;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.dto.WishListDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.model.WishListModel;
import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.repository.WishListRepository;
import com.godigit.bookmybook.util.TokenUtility;
import jakarta.annotation.Resource;
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


        Optional<WishListModel> existingWishList = wishListRepository.findByUserIdAndBookId(user_id, book_id);
        if (existingWishList.isPresent()) {
            throw new RuntimeException("Wishlist entry already exists");
        }
        BookModel bookModel = bookService.getBookModel(book_id, token);
        UserModel user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WishListModel wish_list = WishListModel.builder().book(bookModel).user(user).build();

        WishListModel wish_save = wishListRepository.save(wish_list);

        return WishListConvertor.toDTO(wish_save);
    }

    public List<?> getWishList(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId(); // user id

        UserDTO userDTO = userService.getUserById(userId);

        return userDTO.getWishList();
    }

    public String removeProduct(Long book_id, String token) {
        DataHolder dataHolder = tokenUtility.decode(token); // user_id
        Long user_id = dataHolder.getId();

        wishListRepository.deleteByUserIdAndBookId(user_id, book_id);

        return "Product with " + book_id + " has been removed from Your Book ";

    }


}
