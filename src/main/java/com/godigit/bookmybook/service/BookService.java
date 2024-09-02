package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.BookConvertor;
import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.exception.ForbiddenException;
import com.godigit.bookmybook.exception.ResourceAlreadyExistException;
import com.godigit.bookmybook.exception.ResourceNotFoundException;
import com.godigit.bookmybook.exception.UnauthorizedException;
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

    /**
     * Purpose: This method checks if the user is an admin.
     *
     * @param token This is the user token used for authorization.
     * @throws ForbiddenException If the user is not an admin, this exception is thrown with an appropriate message.
     */

    public void checkAdmin(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        if (!dataHolder.getRole().equalsIgnoreCase("admin"))
            throw new ForbiddenException("You are not authorized :-( ");
    }

    /**
     * Purpose: This method checks if the user is either an admin or a customer.
     *
     * @param token This is the user token used for authorization.
     * @throws UnauthorizedException If the user is neither an admin nor a customer, this exception is thrown with an appropriate message.
     */

    private void checkUser(String token) {
        DataHolder dataHolder = tokenUtility.decode(token);
        if (!dataHolder.getRole().equalsIgnoreCase("admin") &&
                !dataHolder.getRole().equalsIgnoreCase("customer"))
            throw new UnauthorizedException("You are not registered..! :-( ");
    }

    /**
     * Purpose: This method is used to add a new book to the repository.
     *
     * @param token   This is the user token used for authorizing the user.
     *                The user must be an admin to perform this operation.
     * @param bookDTO This is the data transfer object containing the book details.
     * @return BookDTO The response entity containing the newly added BookDTO object.
     * @throws IOException If an input or output exception occurred.
     * @throws ResourceAlreadyExistException If the book already exists in the repository.
     */

    //    TODO : Adding book - if the user  admin
    public BookDTO addBook(String token, BookDTO bookDTO) throws IOException {
        checkAdmin(token);
        List<BookModel> bookIn = bookRepository.findAll().stream().filter(book -> {
            return book.getBookName().equals(bookDTO.getBookName()) && book.getAuthor().equals(bookDTO.getAuthor());
        }).toList();

        if (!bookIn.isEmpty()) {
            throw new ResourceAlreadyExistException("Book already exits ");

        } else {
            BookModel book = new BookModel(bookDTO);
            book.setDescription("a book written by "+book.getAuthor());
            bookRepository.save(book);
            return new BookDTO(book) ;
        }
    }

    /**
     * Purpose: This method is used to retrieve a book by its ID.
     *
     * @param id    The ID of the book to be retrieved.
     * @param token This is the user token used for authorizing the user.
     *              The user must be either an admin or a customer to perform this operation.
     * @return BookDTO The response entity containing the BookDTO object of the retrieved book.
     * @throws ResourceNotFoundException If the book with the specified ID is not found.
     */

    public BookDTO getBookByID(Long id, String token) {
        checkUser(token);
        BookModel book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return BookConvertor.toDTO(book);
    }

    public BookModel getBookModel(Long id,String token){
        checkUser(token);
        return bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book doest exists"));
    }

    /**
     * Purpose: This method is used to retrieve all books from the repository.
     *
     * @param token This is the user token used for authorizing the user.
     *              The user must be either an admin or a customer to perform this operation.
     * @return List<BookModel> The list of all books available in the repository.
     */
    //    TODO: Retrieving all the books -  registered users
    public List<BookModel> getAllBooks(String token) {
        checkUser(token);
        return bookRepository.findAll();
    }

    /**
     * Purpose: This method is used to update the details of an existing book.
     *
     * @param token   This is the user token used for authorizing the user.
     *                The user must be either an admin or a customer to perform this operation.
     * @param id      The ID of the book to be updated.
     * @param bookDTO This is the data transfer object containing the updated book details.
     * @return String A message indicating the result of the update operation.
     */
    //    TODO: Update Book - only if the user admin
    public String updateBook(String token, Long id, BookDTO bookDTO) {

        BookModel book = getBookModel(id,token);

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

    /**
     * Purpose: This method is used to delete a book from the repository.
     *
     * @param token This is the user token used for authorizing the user.
     *              The user must be an admin to perform this operation.
     * @param id    The ID of the book to be deleted.
     * @return String A message indicating the result of the delete operation.
     */
    //    TODO: Deleting book - only if the user  admin
    public String deleteBook(String token, long id) {
        checkAdmin(token);
        BookDTO delete_book = getBookByID(id, token);
        bookRepository.deleteById(delete_book.getId());
        return "Deleted the book with id : " + id;
    }

    /**
     * Purpose: This method is used to change the price of an existing book.
     *
     * @param token  This is the user token used for authorizing the user.
     *               The user must be an admin to perform this operation.
     * @param bookId The ID of the book whose price is to be changed.
     * @param price  The new price of the book.
     * @return String A message indicating the result of the price change operation.
     */

    public String changeBookPrice(String token, long bookId, double price) {
        checkAdmin(token);
        BookDTO updateDTO = BookDTO.builder().price(price).build();
        updateBook(token, bookId, updateDTO);
        return "Changed the price of the book with id " + bookId;
    }

    /**
     * Purpose: This method is used to change the quantity of an existing book.
     *
     * @param token    This is the user token used for authorizing the user.
     *                 The user must be either an admin or a customer to perform this operation.
     * @param book_id  The ID of the book whose quantity is to be changed.
     * @param quantity The new quantity of the book.
     * @return String A message indicating the result of the quantity change operation.
     */
    public String changeBookQuantity(String token, long book_id, int quantity) {

        BookModel bookModel = getBookModel(book_id,token);
        bookModel.setQuantity(quantity);
        bookRepository.save(bookModel);
        return "Changed book quantity for book id " + book_id;
    }

    public String changeQuantityByToken(String token, Long book_id, int quantity) {
        DataHolder dataHolder = tokenUtility.decode(token);
        String role = dataHolder.getRole();
        if (!role.equalsIgnoreCase("Admin"))
            throw new ForbiddenException("You are not authorized  :-) ");
        return changeBookQuantity(token, book_id, quantity);
    }

    /**
     * Purpose: This method is used to add an image to an existing book.
     *
     * @param token    This is the user token used for authorizing the user.
     *                 The user must be an admin to perform this operation.
     * @param image_id The ID of the image to be added to the book.
     * @param bookId   The ID of the book to which the image is to be added.
     * @return BookDTO The response entity containing the updated BookDTO object.
     */
    public BookDTO addBookImage(String token, long image_id, long bookId) {
        checkAdmin(token);
        ImageModel image = imageService.getImageByID(token, image_id);
        BookModel book = getBookModel(bookId,token);
        book.setLogo(image);
        bookRepository.save(book);
        return new BookDTO(book);
    }
}
