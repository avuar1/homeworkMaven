package com.avuar1.entity;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserIT {

    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    void setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        log.info("SessionFactory created");
    }

    @AfterAll
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        log.info("SessionFactory destroyed");
    }

    @Test
    void createUserTest() {

        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("ivam@Gmail.com")
                .password("test")
                .role(Role.CLIENT)
                .build();

        try (Session session1 = sessionFactory.openSession()) {
            session1.beginTransaction();

            session1.save(user);

            User savedUser = session1.get(User.class, user.getId());

            assertNotNull(savedUser);
            assertEquals("Ivan", savedUser.getFirstName());
            assertEquals("Petrov", savedUser.getLastName());
            assertEquals("ivam@Gmail.com", savedUser.getEmail());
            assertEquals("test", savedUser.getPassword());
            assertEquals("CLIENT", savedUser.getRole().toString());

            session1.getTransaction().commit();
        }

        try (Session session2 = sessionFactory.openSession()) {
            session2.beginTransaction();

            session2.delete(user);

            session2.getTransaction().commit();

        }
    }

    @Test
    void updateUserTest() {
        log.info("Running testUpdate");

        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("ivam@Gmail.com")
                .password("test")
                .role(Role.CLIENT)
                .build();

        session.beginTransaction();
        session.save(user);

        user.setFirstName("Petr");
        session.update(user);

        User expectedUser = session.get(User.class, user.getId());

        assertEquals("Petr", expectedUser.getFirstName());

        session.delete(expectedUser);
        session.getTransaction().commit();
    }

    @Test
    void getUserTest() {

        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("iva2@Gmail.com")
                .password("test")
                .role(Role.CLIENT)
                .build();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        }

        try (Session session2 = sessionFactory.openSession()) {
            session2.beginTransaction();

            User foundUser = session2.get(User.class, user.getId());

            assertNotNull(foundUser);

            assertEquals("Ivan", foundUser.getFirstName());
            assertEquals("Petrov", foundUser.getLastName());
            assertEquals("iva2@Gmail.com", foundUser.getEmail());
            assertEquals("test", foundUser.getPassword());
            assertEquals("CLIENT", foundUser.getRole().toString());

            session2.clear();
            session2.delete(user);
            session2.getTransaction().commit();
        }
    }

    @Test
    void deleteUserTest() {
        User user = User.builder()
                .firstName("Ivan")
                .lastName("Petrov")
                .email("iva2@Gmail.com")
                .password("test")
                .role(Role.CLIENT)
                .build();

        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(user);

        User deletedUser = session.get(User.class, user.getId());

        Assertions.assertNull(deletedUser);
    }

    @BeforeEach
    public void  openSession(){
        session = sessionFactory.openSession();
        log.info("Session created");
    }

    @AfterEach
    public void closeSession(){
        if (session != null) {
            session.close();
        }
        log.info("Session closed");
    }
}