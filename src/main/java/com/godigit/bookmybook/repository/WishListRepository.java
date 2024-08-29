package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.WishListModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishListModel,Long> {
             List<WishListModel>findAllById(Long id);
}
