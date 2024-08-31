package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.FeedBackModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedBackRepository extends JpaRepository<FeedBackModel,Long> {
    List<FeedBackModel> findByBookId(Long bookId);
}
