package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.FeedBackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackModel, Long> {
    List<FeedBackModel> findByBookId(Long bookId);

    Optional<FeedBackModel> findByUserIdAndBookId(Long user_id, Long book_id);
}
