package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {


    UserModel findByEmail(String email);
}
