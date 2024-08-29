package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.CartModel;
import com.godigit.bookmybook.model.UserModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartModel,Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM CartModel c WHERE c.id= :ids")
    void deleteAll(@Param("ids") Long ids);
}
