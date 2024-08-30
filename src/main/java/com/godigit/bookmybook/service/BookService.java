package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    UserService userService;

    @Autowired
    TokenUtility tokenUtility;

    private void checkAdmin(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        if (!dataHolder.getRole().equalsIgnoreCase("admin"))
            throw new RuntimeException("You are not authorized :-( ");
    }

    private void checkUser(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        if (!dataHolder.getRole().equalsIgnoreCase("admin") &&
                !dataHolder.getRole().equalsIgnoreCase("customer"))
            throw new RuntimeException("You are not registered..! :-( ");
    }

    //    TODO : Adding book - if the user  admin
    public BookModel addBook(String token, BookDTO bookDTO) {
        checkAdmin(token);
        BookModel book = new BookModel(bookDTO);
        bookRepository.save(book);
        return book;
    }

    //    TODO: Retrieving book by Id - only if the user  admin
    public BookModel getBookByID(Long id, String token) {
//        checkAdmin(token);
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
        return book;
    }

    //    TODO: Retrieving all the books -  registered users
    public List<BookModel> getAllBooks(String token) {
        checkUser(token);
        return bookRepository.findAll();
    }

    //    TODO: Update Book - only if the user admin
    public String updateBook(String token, Long id, BookDTO bookDTO) {
        checkAdmin(token);
        BookModel book = getBookByID(id,token);
        if (bookDTO.getBookName() != null)
            book.setBookName(bookDTO.getBookName());
        if (bookDTO.getAuthor() != null)
            book.setAuthor(bookDTO.getAuthor());
        if (bookDTO.getDescription() != null)
            book.setDescription(bookDTO.getDescription());
        if (bookDTO.getLogo() != null)
            book.setLogo(bookDTO.getLogo());
        if (bookDTO.getPrice() != 0.0)
            book.setPrice(bookDTO.getPrice());
        if (bookDTO.getQuantity() != 0.0)
            book.setQuantity(bookDTO.getQuantity());

        bookRepository.save(book);
        return "Updated the book with id :" + id;
    }

    //    TODO: Deleting book - only if the user  admin
    public String deleteBook(String token,long id) {
        checkAdmin(token);
        BookModel delete_book = getBookByID(id,token);
        bookRepository.deleteById(delete_book.getId());
        return "Deleted the book with id : " + id;
    }

}
