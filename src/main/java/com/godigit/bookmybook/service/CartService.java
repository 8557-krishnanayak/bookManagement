package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.repository.CartRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    CartRepository cartRepo;

    @Autowired
    TokenUtility tokenUtility;

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;


    public CartModel addToCart(String token, long book_id) {
//        List<BookModel> addBook = new ArrayList<>();
//
        System.out.println(token);
        BookModel bookModel = bookService.getBookByID(book_id, token);
//        addBook.add(bookModel);


        DataHolder dataHolder = tokenUtility.decode(token);
        System.out.println(dataHolder);
        UserDTO userDTO = userService.getUserById(dataHolder.getId());
        System.out.println(userDTO);
        UserModel userModel = UserConverter.toEntity(userDTO);



/*
        userModel.set
        if(cartRepo.existsById(dataHolder.getId())) {
            CartModel existingCart = cartRepo.findById(userModel.getId()).orElseThrow();
            existingCart.setBook(addBook);

            existingCart.setTotalPrice((long) (existingCart.getTotalPrice() + bookModel.getPrice()));
            //userService.updateUser(dataHolder.getId(), new UserDTO(userModel));
            cartRepo.save(existingCart);
            return existingCart;
        }
*/


//        System.out.println(userModel);
//
        CartModel cart = userModel.getCart(); // null
//
        System.out.println(cart.getBook() instanceof List<BookModel>);

        try {

            List<BookModel> book = cart.getBook();
            book.add(bookModel);
        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(cart.getBook());
//        System.out.println(userModel);

//        cart.setBook(addBook);
//

        cart.setTotalPrice((long) (cart.getTotalPrice() + bookModel.getPrice()));
        cart.setQuantity(1);
        userModel.setCart(cart);
        System.out.println(userModel);
        userService.updateUser(dataHolder.getId(), UserConverter.toDTO(userModel));

//        CartModel cart = new CartModel();
        return cart;
//        bookService.updateBook(token,book_id,new BookDTO(bookModel));

    }


//
//    public void removeItem(long cartId) {
//        cartRepo.deleteById(cartId);
//    }
//
//    //implement remove by user id here
//
//    public void removeByUserId(long user_id) {
//        List<CartModel> cartItems = cartRepo.findAll();
//        UserDTO required_user = userService.getUserById(user_id);
//        for (CartModel cart : cartItems) {
//            if (cart.getUser().getId() == user_id) {
//                cartRepo.deleteById(user_id);
//            }
//        }
//
//    }
//
//
//    public void updateQuantity(UserDTO user, long cart_id, long update_quantity) {
//        CartModel required_Cart = cartRepo.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart id doesn't exist!"));
//        if (update_quantity > required_Cart.getCart_id())
//            incrementQuantity(user, required_Cart, update_quantity);
//        else
//            decrementQuantity(user, required_Cart, update_quantity);
//    }
//
//    private void incrementQuantity(UserDTO user, CartModel required_Cart, long updateQuantity) {
//
//        List<BookModel> requiredBook = required_Cart.getBook();
//        required_Cart.setQuantity(required_Cart.getQuantity() + updateQuantity);
//
////        required_Cart.setTotalPrice((long) (requiredBook.stream().filter().getPrice() *(required_Cart.getQuantity()+updateQuantity)));
//
//    }
//
//
//    private void decrementQuantity(UserDTO user, CartModel required_Cart, long updateQuantity) {
//        List<BookModel> requiredBook = required_Cart.getBook();
//        required_Cart.setQuantity(required_Cart.getQuantity() - updateQuantity);
//
//        required_Cart.setTotalPrice((long) (requiredBook.getPrice() * (required_Cart.getQuantity() - updateQuantity)));
//
//    }
//
//
//    public List<CartModel> getAllCartItemsForUser(long userId) {
//        List<CartModel> cart = cartRepo.findAll();
//        List<CartModel> userCartItems = new ArrayList<>();
//        for (CartModel cartItems : cart) {
//            CartModel userCartItem = cartRepo.findById(userId).orElseThrow(() -> new RuntimeException("User Id not found"));
//            userCartItems.add(userCartItem);
//
//        }
//        return userCartItems;
//    }
//
//
//    public List<CartModel> getAllCartItems() {
//        return cartRepo.findAll();
//    }
//
//
}

