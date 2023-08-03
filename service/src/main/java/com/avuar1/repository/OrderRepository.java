package com.avuar1.repository;

import com.avuar1.entity.Order;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }
}
