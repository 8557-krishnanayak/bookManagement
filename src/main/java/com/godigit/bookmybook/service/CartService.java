package com.godigit.bookmybook.service;

//import com.godigit.bookmybook.converstion.UserConverter;
import com.godigit.bookmybook.converstion.CartConvertor;
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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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

        CartModel cart = new CartModel();

        cart.setUsers(userModel);
        cart.setBook(bookModel);
        cart.setQuantity(1);
        cart.setTotalPrice((long)bookModel.getPrice());

        CartModel cartModel= cartRepo.save(cart);
        return CartConvertor.toDTO(cartModel);

    }


    public String removeItem(long cartId) {
        cartRepo.deleteById(cartId);

        return "Removed from the cart "+cartId;
    }


    public void updateQuantity(String token, long cart_id, long update_quantity) {

        CartModel required_Cart = cartRepo.findById(cart_id).orElseThrow(() -> new RuntimeException("Cart id doesn't exist!"));
        if (update_quantity > required_Cart.getQuantity())
            incrementQuantity(required_Cart, update_quantity);
        else
            decrementQuantity(required_Cart, update_quantity);
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

        List<UserDTO> cart = userService.getAllUser(token);
        List<CartModel> userCartItems = new ArrayList<>();
        for (UserDTO userDTO : cart) {
            CartModel userCartItem = cartRepo.findById(dataHolder.getId())
                    .orElseThrow(() -> new RuntimeException("User Id not found"));
            userCartItems.add(userCartItem);
        }
        return userCartItems;
    }


    public List<CartModel> getAllCartItems() {
        return cartRepo.findAll();
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

