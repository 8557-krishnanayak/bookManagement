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
        return addWishlist(user_id, bookModel);
    }

    public WishListDTO addWishlist(Long user_id, BookModel bookModel) {

        UserModel user = userRepository.findById(user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        WishListModel wishListModel = user.getWishList() == null ? new WishListModel() : user.getWishList(); // new

        wishListModel.setUserId(user.getId());
        List<BookModel> bookWishList = wishListModel.getBookModelList() == null ? new ArrayList<>() : wishListModel.getBookModelList();


        bookWishList.add(bookModel);
        WishListModel savedWishList = wishListRepository.save(wishListModel);
        return WishListConvertor.toDTO(savedWishList);
    }

    public List<?> getWishList(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId(); // user id

        UserDTO userById = userService.getUserById(userId);
        long id = userById.getWishList().getId();
        List<WishListModel> allById = wishListRepository.findAllById(id);
        return allById;
    }

    public String removeProduct(Long book_id, String token) {
        DataHolder dataHolder = tokenUtility.decode(token); // user_id

        BookModel bookModel = bookService.getBookByID(book_id, token);
//                .orElseThrow(() -> new RuntimeException("product is not wishlisted"));

        System.out.println(bookModel);
//        wishListRepository.deleteById(bookModel.getId());
        wishListRepository.deleteByUserIdAndBookId(dataHolder.getId(), bookModel);
        return "Product with " + book_id + " has been removed from Your Book ";

    }


}
