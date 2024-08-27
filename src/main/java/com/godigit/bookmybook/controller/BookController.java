package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

//    TODO: Calling create api
    @PostMapping()
    public ResponseEntity<?> createBook(@RequestHeader String token,@RequestBody BookDTO bookDTO)  {

        return new ResponseEntity<>(bookService.addBook(token,bookDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookModel> getBook(@RequestHeader String token,@PathVariable long book_id)
    {
        return new ResponseEntity<>(bookService.getBookByID(book_id,token),HttpStatus.FOUND);
    }
    @GetMapping()
    public ResponseEntity<List<BookModel>> getAllBooks(@RequestHeader String token)
    {
        return new ResponseEntity<>(bookService.getAllBooks(token),HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/{book_id}")
    public ResponseEntity<String> deleteBook(@RequestHeader String token,@PathVariable long book_id)
    {
        return new ResponseEntity<>(bookService.deleteBook(token,book_id),HttpStatus.OK);
    }
    @PutMapping("/{book_id}")
    public ResponseEntity<String> updateBook(@RequestHeader String token,@PathVariable long book_id,@RequestBody BookDTO bookDTO)
    {
        return new ResponseEntity<>(bookService.updateBook(token,book_id,bookDTO),HttpStatus.FOUND);
    }

}
