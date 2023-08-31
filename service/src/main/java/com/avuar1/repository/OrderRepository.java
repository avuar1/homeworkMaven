package com.avuar1.repository;

import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByOrderStatus(OrderStatus status);
}