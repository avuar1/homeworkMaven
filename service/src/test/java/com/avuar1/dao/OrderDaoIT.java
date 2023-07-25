package com.avuar1.dao;

import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import com.avuar1.util.HibernateUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestDataImporter;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OrderDaoIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final OrderDao orderDao = OrderDao.getInstance();
    private Session session; // Сессия одна на все тесты

    @BeforeAll
    public static void start() {

    }

    @AfterAll
    public static void finish() {
        sessionFactory.close();
    }

    @BeforeEach
    public void setup() {
        session = sessionFactory.openSession(); // сессия открывается в @BeforeEach
        session.beginTransaction(); // и здесь же открываем транзакцию
        TestDataImporter.importData(session); // Генерация данных для тестов
    }

    @AfterEach
    void closeAll() {
        session.getTransaction().rollback(); // откатываем транзакцию после теста, чтобы он не сохранял данные в БД
        session.close(); //Закрываем сессию

    }

    @Test
    void findAllOrders() {

        List<Order> results = orderDao.findAll(session);
        assertThat(results).hasSize(3);

    }

    @Test
    void findOrdersByStatus() {
        List<Order> results = orderDao.findByStatus(session, OrderStatus.ACCEPTED);
        assertThat(results).hasSize(1);
    }

}