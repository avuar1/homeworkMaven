package com.avuar1.dao;

import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import static com.avuar1.entity.QOrder.order;

@NoArgsConstructor
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    public List<Order> findAll(Session session) {
        return session.createQuery("select o from Order o", Order.class)
                .list();
    }

    public List<Order> findByStatus(Session session, OrderStatus orderStatus) {
        return new JPAQuery<Order>(session)
                .select(order)
                .from(order)
                .where(order.orderStatus.eq(orderStatus))
                .fetch();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
