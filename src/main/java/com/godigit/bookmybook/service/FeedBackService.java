package com.godigit.bookmybook.service;

import com.godigit.bookmybook.converstion.FeedbackConverter;
import com.godigit.bookmybook.dto.DataHolder;
import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.model.UserModel;
import com.godigit.bookmybook.repository.FeedBackRepository;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.util.TokenUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public FeedBackDTO createFeedBack(String token, Long bookId, FeedBackDTO feedbackDTO) {
        DataHolder dataHolder = tokenUtility.decode(token);
        Long userId = dataHolder.getId();
        BookModel bookModel = bookService.getBookByID(bookId, token);

        return addFeedBack(userId, bookModel, feedbackDTO);
    }

    public FeedBackDTO addFeedBack(Long userId, BookModel book, FeedBackDTO feedbackDTO) {

        UserModel user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        FeedBackModel feedback = new FeedBackModel();
        feedback.setUser(user);
        feedback.setBook(book);
        feedback.setComments(feedbackDTO.getComments());
        feedback.setRating(feedbackDTO.getRating());


        FeedBackModel save = feedBackRepository.save(feedback);

        return FeedbackConverter.toDTO(save);
    }

    public List<FeedBackDTO> getFeedback(Long bookId) {
        List<FeedBackModel> feedbackList = feedBackRepository.findByBookId(bookId);
        return feedbackList.stream()
                .map(FeedbackConverter::toDTO)
                .collect(Collectors.toList());
    }

    public List<FeedBackModel> getFeedbackOfUser() {
        List<FeedBackModel> feedBackDTOList = feedBackRepository.findAll();
        return feedBackDTOList;
    }
}
