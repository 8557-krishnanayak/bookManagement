package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.ImageModel;
import com.godigit.bookmybook.repository.BookRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ImageService imageService;


    @Autowired
    TokenUtility tokenUtility;

    public void checkAdmin(String token) {
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
    public ResponseEntity<?> addBook(String token, BookDTO bookDTO) throws IOException {
        checkAdmin(token);
        List<BookModel> bookIn = bookRepository.findAll().stream().filter(book -> {
            return book.getBookName().equals(bookDTO.getBookName()) && book.getAuthor().equals(bookDTO.getAuthor());
        }).toList();

        if (!bookIn.isEmpty()) {
            throw new RuntimeException("Book already exits ");

        } else {
            BookModel book = new BookModel(bookDTO);
//            book.setLogo(logo.getBytes());
            bookRepository.save(book);
            return new ResponseEntity<>(book, HttpStatus.ACCEPTED);
        }
    }

    //    TODO: Retrieving book by Id - only if the user  admin
    public BookModel getBookByID(Long id, String token) {
        checkUser(token);
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

        BookModel book = getBookByID(id, token);
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
    public String deleteBook(String token, long id) {
        checkAdmin(token);
        BookModel delete_book = getBookByID(id, token);
        bookRepository.deleteById(delete_book.getId());
        return "Deleted the book with id : " + id;
    }

    public String changeBookPrice(String token, long bookId, double price) {
        checkAdmin(token);
        BookDTO updateDTO = BookDTO.builder().price(price).build();
        updateBook(token, bookId, updateDTO);
        return "Changed the price of the book with id " + bookId;
    }

    public String changeBookQuantity(String token, long book_id, int quantity) {
        BookModel bookModel = getBookByID(book_id, token);
        bookModel.setQuantity(quantity);
        bookRepository.save(bookModel);
        return "Changed book quantity for book id " + book_id;
    }

    public ResponseEntity<?> changeQuantityByToken(String token, Long book_id, int quantity) {
        DataHolder dataHolder = tokenUtility.decode(token);
        String role = dataHolder.getRole();
        if (!role.equalsIgnoreCase("Admin"))
            throw new RuntimeException("You are not authorized  :-) ");
        return new ResponseEntity<>(changeBookQuantity(token, book_id, quantity), HttpStatus.ACCEPTED);
    }

    public BookModel addBookImage(String token, long image_id, long bookId) {
        checkAdmin(token);
        ImageModel image = imageService.getImageByID(token, image_id);
        BookModel book = getBookByID(bookId, token);
        book.setLogo(image);
        bookRepository.save(book);
        return book;
    }
}
