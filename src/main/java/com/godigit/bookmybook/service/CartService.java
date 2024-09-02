package com.godigit.bookmybook.service;

//import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.converstion.CartConvertor;
import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.CartDto;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.UserDTO;
import com.godigit.bookmybook.exception.BookLimitException;
import com.godigit.bookmybook.exception.ResourceNotFoundException;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;

import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.repository.CartRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    public CartDto addToCart(String token, long book_id) {

        DataHolder dataHolder=tokenUtility.decode(token);
        UserModel userModel = userService.getUserModalById(dataHolder.getId());
        BookModel bookModel= bookService.getBookByID(book_id,token);

        if(bookModel!=null){
            List<CartModel> existingUsers = cartRepo.findAll();
            for (CartModel cart : existingUsers) {
                if ((cart.getUsers().getId().equals(dataHolder.getId())) && (cart.getBook().getId() == book_id)) {
                    cart.setQuantity(cart.getQuantity() + 1);
                    cart.setTotalPrice((long) (cart.getQuantity() * bookModel.getPrice()));

                    cartRepo.save(cart);
                    return CartConvertor.toDTO(cart);
                }
            }

                CartModel cart = new CartModel();

                cart.setUsers(userModel);
                cart.setBook(bookModel);
                cart.setQuantity(1);
                cart.setTotalPrice((long) bookModel.getPrice());

                CartModel cartModel = cartRepo.save(cart);
                return CartConvertor.toDTO(cartModel);
        }else throw new ResourceNotFoundException("Invalid Book ID!");

    }


    public String removeItem(long cartId) {
        cartRepo.deleteById(cartId);

        return "Removed from the cart "+cartId;
    }



    public String increaseQuantity(String token, long cart_id, long update_quantity) {

        CartModel required_Cart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("Cart id doesn't exist!"));
        BookModel required_book = required_Cart.getBook();

        if(required_book.getPrice() >= update_quantity ) {
            required_Cart.setQuantity(update_quantity + required_Cart.getQuantity());
            required_Cart.setTotalPrice((long) (required_book.getPrice() * (update_quantity + required_Cart.getQuantity())));
        }else throw new BookLimitException("There is no stock for this book product:" +required_book.getId());

        cartRepo.save(required_Cart);

        return "Quantity increased for the cart id :"+cart_id;
    }

    public String decreaseQuantity(String token, long cart_id, long update_quantity) {
        CartModel required_Cart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("Cart id doesn't exist!"));
        BookModel required_book = required_Cart.getBook();

        required_Cart.setQuantity(required_Cart.getQuantity()-update_quantity);
        required_Cart.setTotalPrice((long) (required_book.getPrice()* (required_Cart.getQuantity())-update_quantity));

        cartRepo.save(required_Cart);

        return "Quantity decreased for the cart_id: "+cart_id;
    }


    public List<CartModel> getAllCartItemsForUser(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        UserModel userModel = userService.getUserModalById(dataHolder.getId());

        List<CartModel> cart = userModel.getCart();
        return cart;
    }


    public ResponseEntity<?> getAllCartItems(String token) {

        if (isAdmin(token)) {
            List<CartModel> cartItems = cartRepo.findAll();
            return new ResponseEntity<>(cartItems, HttpStatus.OK);
        } else {
            String message = "You do not have access to view all cart items.";
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
    }
    public boolean isAdmin(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        UserModel user = userService.getUserModalById(dataHolder.getId());
        System.out.println(user.getRole());
        return user != null && user.getRole().equalsIgnoreCase("ADMIN");
    }

    @Transactional
    public String removeByUserId(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        UserModel userModel = userService.getUserModalById(dataHolder.getId());

        List<CartModel> cart = userModel.getCart();

        for(CartModel c : cart){
            cartRepo.deleteAll(c.getId());
        }
        return "Removed ";
    }
}

