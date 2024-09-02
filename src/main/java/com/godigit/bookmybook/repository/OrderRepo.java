package com.godigit.bookmybook.repository;

import com.godigit.bookmybook.model.OrderModel;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OrderRepo extends JpaRepository<OrderModel, Long> {
    public List<OrderModel> findByCancel(boolean cancel);
    public List<OrderModel> findByUserId(long userId);

}
