package com.godigit.bookmybook.service;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

//    TODO : Adding a new book
    public BookModel addBook(String token,BookDTO bookDTO)  {
        BookModel book = new BookModel(bookDTO);
        bookRepository.save(book);
        return book;
    }

//    TODO: Retrieving the book using its Id
    public BookModel getBookByID(Long id)
    {
        BookModel book = bookRepository.findById(id).orElseThrow(()->new RuntimeException("Book not found"));
        return book;
    }

//    TODO: Retrieving all the books
    public List<BookModel> getAllBooks()
    {
        return bookRepository.findAll();
    }

//    TODO: Update Book
    public String updateBook(Long id,BookDTO bookDTO)
    {
        BookModel book = getBookByID(id);
        if(bookDTO.getBookName()!=null)
            book.setBookName(bookDTO.getBookName());
        if(bookDTO.getAuthor()!=null)
            book.setAuthor(bookDTO.getAuthor());
        if(bookDTO.getDescription()!=null)
            book.setDescription(bookDTO.getDescription());
        if(bookDTO.getLogo()!=null)
            book.setLogo(bookDTO.getLogo());
        if(bookDTO.getPrice()!=0.0)
            book.setPrice(bookDTO.getPrice());
        if(bookDTO.getQuantity()!=0.0)
            book.setQuantity(bookDTO.getQuantity());

        bookRepository.save(book);
        return "Updated the book with id :"+id;
    }

//    TODO: Deleting the book
    public String deleteBook(long id)
    {
        BookModel delete_book = getBookByID(id);
        bookRepository.deleteById(delete_book.getId());
        return "Deleted the book with id : "+id;
    }

}
