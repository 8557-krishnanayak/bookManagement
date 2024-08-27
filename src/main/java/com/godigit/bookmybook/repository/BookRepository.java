package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.dto.BookDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookDTO,Long> {
}
