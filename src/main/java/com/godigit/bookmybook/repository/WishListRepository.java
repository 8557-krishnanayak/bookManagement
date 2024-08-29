package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.WishListModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishListModel,Long> {

}
