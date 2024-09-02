package com.godigit.bookmybook.controller;

import com.godigit.bookmybook.dto.FeedBackDTO;
import com.godigit.bookmybook.model.FeedBackModel;
import com.godigit.bookmybook.repository.UserRepository;
import com.godigit.bookmybook.service.FeedBackService;
import com.godigit.bookmybook.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<FeedBackDTO> createFeedBack(@RequestHeader String token, @RequestParam Long book_id, @RequestBody FeedBackDTO feedBackDTO) {
        FeedBackDTO addedFeedBack = feedBackService.createFeedBack(token, book_id, feedBackDTO);
        return new ResponseEntity<>(addedFeedBack, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<List<FeedBackDTO>> getFeedBack(@RequestHeader String token) {
        List<FeedBackDTO> feedbackList = feedBackService.getUserFeedback(token);
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }
    @GetMapping("/get/book")
    public ResponseEntity<List<FeedBackDTO>> getFeedBack(@RequestHeader String token,@RequestParam Long book_id) {
        List<FeedBackDTO> feedbackList = feedBackService.getBookFeedbacks(token,book_id);
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<?>> getFeedBackM() {
        List<FeedBackModel> feedbackList = feedBackService.getFeedbackOfUser();
        return new ResponseEntity<>(feedbackList, HttpStatus.OK);
    }

//    @GetMapping("/user")
//    public ResponseEntity<?> getUser() {
//        feedBackService.getFeedBackByuserID();
//        return new ResponseEntity<>(userRepository.findById(1l).orElse(null), HttpStatus.OK);
//    }

    @PutMapping
    public ResponseEntity<FeedBackDTO> updateFeedBack(@RequestHeader String token, @RequestParam Long feedback_id,@Valid @RequestBody FeedBackDTO feedBackDTO) {
        FeedBackDTO updatedFeedBack = feedBackService.updateFeedBack(token, feedback_id, feedBackDTO);
        return new ResponseEntity<>(updatedFeedBack, HttpStatus.OK);
    }

}
