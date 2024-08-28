package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartModel,Long> {
}
