package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    //    TODO: Adding new book using this API
    @PostMapping()
    public ResponseEntity<?> createBook(@RequestHeader String token, @Valid @RequestBody BookDTO bookDTO) throws IOException {

        return new ResponseEntity<>(bookService.addBook(token, bookDTO), HttpStatus.CREATED);
    }

    //    TODO: Retrieving a particular book with id using this API
    @GetMapping("/{book_id}")
    public ResponseEntity<BookModel> getBook(@RequestHeader String token, @PathVariable long book_id) {
        return new ResponseEntity<>(bookService.getBookByID(book_id, token), HttpStatus.FOUND);
    }

    //    TODO: Retrieving all books using this API
    @GetMapping()
    public ResponseEntity<List<BookModel>> getAllBooks(@RequestHeader String token) {
        return new ResponseEntity<>(bookService.getAllBooks(token), HttpStatus.ACCEPTED);
    }

    //    TODO: Deleting the book with id using this API
    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@RequestHeader String token, @PathVariable long book_id) {
        return new ResponseEntity<>(bookService.deleteBook(token, book_id), HttpStatus.OK);
    }

    //    TODO: Updating the book with id using this API
    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(@RequestHeader String token, @PathVariable long book_id, @RequestBody BookDTO bookDTO) {
        return new ResponseEntity<>(bookService.updateBook(token, book_id, bookDTO), HttpStatus.FOUND);
    }

    //    TODO: Changing  the book quantity using this API
    @PutMapping("/changequantity/{book_id}/{quantity}")
    public ResponseEntity<?> change(@RequestHeader String token, @PathVariable long book_id, @PathVariable int quantity) {
        return new ResponseEntity<>(bookService.changeQuantityByToken(token, book_id, quantity), HttpStatus.ACCEPTED);
    }

    //    TODO: Changing the price of a book using this API
    @PutMapping("/changeprice/{book_id}/{price}")
    public ResponseEntity<String> changeBookPrice(@RequestHeader String token, @PathVariable long book_id, @PathVariable double price) {
        return new ResponseEntity<>(bookService.changeBookPrice(token, book_id, price), HttpStatus.FOUND);
    }

}
