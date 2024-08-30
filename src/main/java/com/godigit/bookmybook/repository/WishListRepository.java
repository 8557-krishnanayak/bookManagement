package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.BookModel;
import com.godigit.bookmybook.model.WishListModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishListModel, Long> {
    List<WishListModel> findAllById(Long id);

    //void deleteByBookId(Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM WishListModel w WHERE w.userId = :userId AND :book MEMBER OF w.bookModelList")
    void deleteByUserIdAndBookId(Long userId, BookModel book);


}
