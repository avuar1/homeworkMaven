package com.avuar1.entity;

import com.avuar1.util.HibernateUtil;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserIT {

    private SessionFactory sessionFactory;

    @BeforeAll
    void setup() {
        sessionFactory = HibernateUtil.buildSessionFactory();
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

            transaction.commit();

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

            session.save(user);

            session.flush();
            session.evict(user);

            transaction.commit();

            Transaction transaction2 = session.beginTransaction();

            User foundUser = session.get(User.class, user.getId());

            assertNotNull(foundUser);

            assertEquals("Ivan", foundUser.getFirstName());
            assertEquals("Petrov", foundUser.getLastName());
            assertEquals("iva2@Gmail.com", foundUser.getEmail());
            assertEquals("test", foundUser.getPassword());
            assertEquals("CLIENT", foundUser.getRole().toString());

            transaction2.commit();
        }
    }
}