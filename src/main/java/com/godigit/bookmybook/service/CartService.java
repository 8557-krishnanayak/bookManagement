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
        BookModel bookModel= bookService.getBookModel(book_id,token);

        if(bookModel.getQuantity()>=1 && bookModel!=null) {

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

//        else throw new BookLimitException("There is no stock for this book product: "+book_id);
    }


    public String removeItem(long cartId) {
        cartRepo.deleteById(cartId);

        return "Removed from the cart "+cartId;
    }


    public String updateQuantity(String token, long cart_id, long update_quantity) {

        CartModel required_Cart = cartRepo.findById(cart_id).orElseThrow(() -> new ResourceNotFoundException("Cart id doesn't exist!"));
        if (update_quantity > required_Cart.getQuantity())
            incrementQuantity(required_Cart, update_quantity);
        else
            decrementQuantity(required_Cart, update_quantity);

        return "Cart updated for the iD: "+cart_id;
    }

    private void incrementQuantity(CartModel required_Cart, long updateQuantity) {

        BookModel requiredBook = required_Cart.getBook();
        required_Cart.setQuantity(updateQuantity+required_Cart.getQuantity());
        required_Cart.setTotalPrice((long) (requiredBook.getPrice()* (updateQuantity+required_Cart.getQuantity())));

        cartRepo.save(required_Cart);

    }

    private void decrementQuantity(CartModel required_Cart, long updateQuantity) {

        BookModel requiredBook = required_Cart.getBook();
        required_Cart.setQuantity(updateQuantity- required_Cart.getQuantity());
        required_Cart.setTotalPrice((long) (requiredBook.getPrice()* (updateQuantity- required_Cart.getQuantity())));

        cartRepo.save(required_Cart);
    }


    public List<CartModel> getAllCartItemsForUser(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        UserModel userModel = userService.getUserModalById(dataHolder.getId());

        List<CartModel> cart = userModel.getCart();
//        List<CartModel> userCartItems = new ArrayList<>();
//
//        for(CartModel c : cart){
//            userCartItems.add(c);
//        }
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

