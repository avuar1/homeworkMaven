package com.avuar1.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserIT {

    private static SessionFactory sessionFactory;

    @BeforeAll
    void setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @AfterAll
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @Test
    void saveUserTest() {
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            User user = User.builder()
                    .firstName("Ivan")
                    .lastName("Petrov")
                    .email("ivam@Gmail.com")
                    .password("test")
                    .role(Role.CLIENT)
                    .build();

            session.save(user);
            session.flush();
            session.evict(user);

            User savedUser = session.get(User.class, user.getId());

            assertNotNull(savedUser);

            assertEquals("Ivan", savedUser.getFirstName());
            assertEquals("Petrov", savedUser.getLastName());
            assertEquals("ivam@Gmail.com", savedUser.getEmail());
            assertEquals("test", savedUser.getPassword());
            assertEquals("CLIENT", savedUser.getRole().toString());


            transaction.rollback();
            session.delete(user);
            session.clear();
        }
    }

    @Test
    void findUserTest() {
        try (Session session = sessionFactory.openSession()) {

            Transaction transaction = session.beginTransaction();

            User user = User.builder()
                    .firstName("Ivan")
                    .lastName("Petrov")
                    .email("iva2@Gmail.com")
                    .password("test")
                    .role(Role.CLIENT)
                    .build();
            // Сохранили юзера
            session.save(user);
            // Пропихнули юзера
            session.flush();
            //убрали Юзера из контекста
            session.evict(user);
            // закрыли транзакцию
            transaction.commit();
            session.clear();
            // Открыли вторую транзакцию
            Transaction transaction2 = session.beginTransaction();

            // добавили юзера в контекст
            User foundUser = session.get(User.class, user.getId());
            // сравнили
            assertNotNull(foundUser);

            assertEquals("Ivan", foundUser.getFirstName());
            assertEquals("Petrov", foundUser.getLastName());
            assertEquals("iva2@Gmail.com", foundUser.getEmail());
            assertEquals("test", foundUser.getPassword());
            assertEquals("CLIENT", foundUser.getRole().toString());

//            // откатили транзакцию
            transaction2.rollback();
            // удалили юзера из БД
            session.delete(user);
            transaction2.commit();
            // очистили сессию
            session.clear();
        }
    }
}