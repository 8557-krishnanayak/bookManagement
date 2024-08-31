package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.WishListModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishListModel, Long> {
    List<WishListModel> findAllById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM WishListModel w WHERE w.user.id = :userId AND w.book.id = :bookId")
    void deleteByUserIdAndBookId(Long userId, Long bookId);

    Optional<WishListModel> findByUserIdAndBookId(Long userId, Long bookId);
}
