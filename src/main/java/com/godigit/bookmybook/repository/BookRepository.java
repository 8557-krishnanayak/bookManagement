package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.dto.BookDTO;
import com.godigit.bookmybook.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel,Long> {
}
