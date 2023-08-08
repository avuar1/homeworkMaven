package com.avuar1.repository;

import com.avuar1.entity.Order;

import com.avuar1.entity.OrderStatus;
import static com.avuar1.entity.QOrder.order;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class OrderRepository extends RepositoryBase<Integer, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findByStatus(OrderStatus orderStatus) {
        return new JPAQuery<Order>(getEntityManager())
                .select(order)
                .from(order)
                .where(order.orderStatus.eq(orderStatus))
                .fetch();
    }
}
