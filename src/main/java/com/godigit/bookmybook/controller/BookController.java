package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.exception.ForbiddenException;
import com.godigit.bookmybook.exception.UnauthorizedException;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    /**
     * Purpose: This API is used to create a new book.
     *
     * @param token   This is the user token given in a @RequestHeader("Authorization") for authorizing the user.
     *                If the user is an admin, they have access to perform all operations on the book.
     * @param bookDTO The response coming from the front end.
     * @return ResponseEntity<BookDTO> The response entity containing the result BookDTO object.
     *                 With the status code given.
     */
    @PostMapping()
    public ResponseEntity<BookDTO> createBook(@RequestHeader("Authorization") String token, @Valid @RequestBody BookDTO bookDTO) throws IOException {

        return new ResponseEntity<>(bookService.addBook(token, bookDTO), HttpStatus.CREATED);
    }

    /**
     * Purpose: This API is used to add an image to a book.
     *
     * @param token    The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param image_id The ID of the image to be added.
     * @param book_id  The ID of the book to which the image will be added.
     * @return ResponseEntity<BookDTO> The response entity containing the result of the image addition.
     */

    @PutMapping("/addimage/{book_id}/{image_id}")
    public ResponseEntity<BookDTO> addImage(@RequestHeader("Authorization") String token,@PathVariable long image_id,@PathVariable long book_id) throws IOException {
        return new ResponseEntity<>(bookService.addBookImage(token,image_id,book_id),HttpStatus.FOUND);
    }

    /**
     * Purpose: This API is used to retrieve a particular book by its ID.
     *
     * @param token   The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param book_id The ID of the book to be retrieved.
     * @return ResponseEntity<BookModel> The response entity containing the book model in the form of dto.
     *
     */

    @GetMapping("/{book_id}")
    public ResponseEntity<BookDTO> getBook(@RequestHeader("Authorization") String token, @PathVariable long book_id) {
        return new ResponseEntity<>(bookService.getBookByID(book_id, token), HttpStatus.FOUND);
    }

    /**
     * Purpose: This API is used to retrieve all books.
     *
     * @param token The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @return ResponseEntity<List<BookModel>> The response entity containing the list of all books.
     */

    @GetMapping()
    public ResponseEntity<List<BookModel>> getAllBooks(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(bookService.getAllBooks(token), HttpStatus.ACCEPTED);
    }

    /**
     * Purpose: This API is used to delete a book by its ID.
     *
     * @param token   The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param book_id The ID of the book to be deleted.
     * @return ResponseEntity<String> The response entity containing the result of the book deletion.
     */
    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@RequestHeader("Authorization") String token, @PathVariable long book_id) {
        return new ResponseEntity<>(bookService.deleteBook(token, book_id), HttpStatus.OK);
    }

    /**
     * Purpose: This API is used to update a book by its ID.
     *
     * @param token   The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param book_id The ID of the book to be updated.
     * @param bookDTO The data transfer object containing the updated book information.
     * @return ResponseEntity<String> The response entity containing the result of the book update.
     */
    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(@RequestHeader("Authorization") String token, @PathVariable long book_id, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.updateBook(token, book_id, bookDTO), HttpStatus.FOUND);
    }

    /**
     * Purpose: This API is used to change the quantity of a book.
     *
     * @param token    The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param book_id  The ID of the book whose quantity is to be changed.
     * @param quantity The new quantity of the book.
     * @return ResponseEntity<?> The response entity containing the result of the quantity change.
     */
    @PutMapping("/changequantity/{book_id}/{quantity}")
    public ResponseEntity<String> change(@RequestHeader("Authorization") String token, @PathVariable long book_id, @PathVariable int quantity) {
        return new ResponseEntity<>(bookService.changeQuantityByToken(token, book_id, quantity), HttpStatus.ACCEPTED);
    }

    /**
     * Purpose: This API is used to change the price of a book.
     *
     * @param token   The user token given in a @RequestHeader("Authorization") for authorizing the user.
     * @param book_id The ID of the book whose price is to be changed.
     * @param price   The new price of the book.
     * @return ResponseEntity<String> The response entity containing the result of the price change.
     */
    @PutMapping("/changeprice/{book_id}/{price}")
    public ResponseEntity<String> changeBookPrice(@RequestHeader("Authorization") String token, @PathVariable long book_id, @PathVariable double price) {
        return new ResponseEntity<>(bookService.changeBookPrice(token, book_id, price), HttpStatus.FOUND);
    }

}
