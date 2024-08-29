package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageModel,Long> {
}
