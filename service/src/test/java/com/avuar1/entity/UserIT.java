//package com.avuar1.entity;
//
//import java.time.*;
//import lombok.extern.slf4j.Slf4j;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.junit.jupiter.api.*;
//import util.HibernateTestUtil;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@Slf4j
////@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class UserIT {
//
//    private static SessionFactory sessionFactory;
//    private static Session session;
//
//    @BeforeAll
//    static void setup() {
//        sessionFactory = HibernateTestUtil.buildSessionFactory();
//    }
//
//    @BeforeEach
//    public void openSession() {
//        session = sessionFactory.openSession();
//        session.beginTransaction();
//    }
//
//
////    В идеале должна быть ровно одна транзакция для одного теста!
////    И она должна открываться в BeforeEach
////    Дальше выполняются все ваши тесты, и работа с базой данной и с сущностями, в рамках этой Транзакции
////    И только потом в AfterEach проихсходит rollback транзакции, ключевое слово rollback,
////    т.е мы не комитим транзакцию, потом заново что то чистим перед каждым тестом, мы просто делаем ролбэк
////    и все изменения в рамках нашего теста в базе данных не останутся.
//
//
//    @Test
//    void createUserTest() {
//
//        User user = createUserAndCustumerData();
//
//        session.save(user);
//        session.flush();
//
//        User savedUser = session.get(User.class, user.getId());
//
//        assertNotNull(savedUser);
//        assertEquals("Ivan", savedUser.getFirstName());
//        assertEquals("Petrov", savedUser.getLastName());
//        assertEquals("ivam@Gmail.com", savedUser.getEmail());
//        assertEquals("test", savedUser.getPassword());
//        assertEquals("CLIENT", savedUser.getRole().toString());
//
//    }
//
//    @Test
//    void updateUserTest() {
//
//        User user = User.builder()
//                .firstName("Ivan")
//                .lastName("Petrov")
//                .email("ivam@Gmail.com")
//                .password("test")
//                .role(Role.CLIENT)
//                .build();
//
//        session.beginTransaction();
//        session.save(user);
//
//        user.setFirstName("Petr");
//        session.update(user);
//
//        User expectedUser = session.get(User.class, user.getId());
//
//        assertEquals("Petr", expectedUser.getFirstName());
//
//        session.delete(expectedUser);
//        session.getTransaction().commit();
//    }
//
//    @Test
//    void getUserTest() {
//
//        User user = User.builder()
//                .firstName("Ivan")
//                .lastName("Petrov")
//                .email("iva2@Gmail.com")
//                .password("test")
//                .role(Role.CLIENT)
//                .build();
//
//        try (Session session = sessionFactory.openSession()) {
//            session.beginTransaction();
//
//            session.save(user);
//
//            session.getTransaction().commit();
//        }
//
//        try (Session session2 = sessionFactory.openSession()) {
//            session2.beginTransaction();
//
//            User foundUser = session2.get(User.class, user.getId());
//
//            assertNotNull(foundUser);
//
//            assertEquals("Ivan", foundUser.getFirstName());
//            assertEquals("Petrov", foundUser.getLastName());
//            assertEquals("iva2@Gmail.com", foundUser.getEmail());
//            assertEquals("test", foundUser.getPassword());
//            assertEquals("CLIENT", foundUser.getRole().toString());
//
//            session2.clear();
//            session2.delete(user);
//            session2.getTransaction().commit();
//        }
//    }
//
//    @Test
//    void deleteUserTest() {
//        User user = User.builder()
//                .firstName("Ivan")
//                .lastName("Petrov")
//                .email("iva2@Gmail.com")
//                .password("test")
//                .role(Role.CLIENT)
//                .build();
//
//        session.beginTransaction();
//        session.save(user);
//        session.getTransaction().commit();
//
//        session.beginTransaction();
//        session.delete(user);
//
//        User deletedUser = session.get(User.class, user.getId());
//
//        Assertions.assertNull(deletedUser);
//    }
//
//
//    @AfterEach
//    public void closeSession() {
//        session.getTransaction().rollback();
//        if (session != null) {
//            session.close();
//        }
//    }
//
//    @AfterAll
//    static void tearDown() {
//        if (sessionFactory != null) {
//            sessionFactory.close();
//        }
//    }
//
//    public User createUserAndCustumerData() {
//        User user = User.builder()
//                .firstName("Ivan")
//                .lastName("Petrov")
//                .email("ivam@Gmail.com")
//                .password("test")
//                .role(Role.CLIENT)
//                .build();
//
//        CustomerData.builder()
//                .user(user)
//                .driverLicenseNumber("343423423")
//                .driverLicenseExpiration(LocalDate.of(2024, 01, 23))
//                .creditAmount(1450.00)
//                .build();
//
//        return user;
//    }
//
//}