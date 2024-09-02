package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.FeedbackConverter;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.exception.ResourceAlreadyExistException;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.FeedBackRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeedBackService {

    @Autowired
    private FeedBackRepository feedBackRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private TokenUtility tokenUtility;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public FeedBackDTO createFeedBack(String token, Long bookId, FeedBackDTO feedbackDTO) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId();
        BookModel bookModel = bookService.getBookModel(bookId, token);

        Optional<FeedBackModel> feedBackModel = feedBackRepository.findByUserIdAndBookId(dataHolder.getId(), bookId);
        if (feedBackModel.isPresent()) throw new ResourceAlreadyExistException("same user can't give the feedback again");

        return addFeedBack(userId, bookModel, feedbackDTO);
    }

    public FeedBackDTO addFeedBack(Long userId, BookModel book, FeedBackDTO feedbackDTO) {

        UserModel user = userService.getUserModalById(userId);

        FeedBackModel feedback = new FeedBackModel();
        feedback.setUser(user);
        feedback.setBook(book);
        feedback.setComments(feedbackDTO.getComments());
        feedback.setRating(feedbackDTO.getRating());


        FeedBackModel save = feedBackRepository.save(feedback);

        return FeedbackConverter.toDTO(save);
    }

    public List<FeedBackDTO> getUserFeedback(String token) {
        Long user_id = tokenUtility.decode(token).getId();

        UserModel userModalById = userService.getUserModalById(user_id);
        List<FeedBackModel> feedbacksList = userModalById.getFeedbacks();

        return feedbacksList.stream()
                .map(FeedbackConverter::toDTO)
                .collect(Collectors.toList());
    }
    public List<FeedBackDTO> getBookFeedbacks(String token,Long book_id) {

        BookModel BookModalById = bookService.getBookModel(book_id,token);
        List<FeedBackModel> feedbacksList = BookModalById.getFeedBack();

        return feedbacksList.stream()
                .map(FeedbackConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<FeedBackModel> getFeedbackOfUser() {
        List<FeedBackModel> feedBackDTOList = feedBackRepository.findAll();
        return feedBackDTOList;
    }

    public FeedBackDTO updateFeedBack(String token, Long feedbackId, FeedBackDTO feedbackDTO) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId();

        FeedBackModel feedback = feedBackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback not found"));

        if (!feedback.getUser().getId().equals(userId)) {
            throw new RuntimeException("Unauthorized to update this feedback");
        }

        feedback.setComments(feedbackDTO.getComments());
        feedback.setRating(feedbackDTO.getRating());

        FeedBackModel updatedFeedback = feedBackRepository.save(feedback);

        return FeedbackConverter.toDTO(updatedFeedback);
    }

}
