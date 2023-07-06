package com.avuar1.entity;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarCategoryIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private CarCategory carCategory;

    @BeforeAll
    void setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
        log.info("SessionFactory created");
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        log.info("Session created");

        carCategory = CarCategory.builder()
                .categoryLevel(CategoryLevel.PREMIUM)
                .dayPrice(1500.00)
                .cars(new ArrayList<>())
                .build();

    }

    @Test
    void createCarCategoryTest() {
        session.beginTransaction();
        session.save(carCategory);

        CarCategory actualRezult = session.get(CarCategory.class, carCategory.getId());

        assertNotNull(actualRezult.getId());
        assertEquals(actualRezult.getId(), carCategory.getId());
        assertEquals("PREMIUM", actualRezult.getCategoryLevel().toString());

        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.getTransaction().commit();
    }

    @Test
    void getCarCategoryTest() {
        session.beginTransaction();
        session.save(carCategory);

        session.getTransaction().commit();

        session.beginTransaction();

        CarCategory actualRezult = session.get(CarCategory.class, carCategory.getId());

        assertNotNull(actualRezult);
        assertEquals(actualRezult, carCategory);

        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.getTransaction().commit();

    }

    @Test
    void updateCarCategoryTest() {
        session.beginTransaction();
        session.save(carCategory);
        carCategory.setDayPrice(1300.00);

        session.update(carCategory);

        CarCategory actualRezult = session.get(CarCategory.class, carCategory.getId());

        assertEquals(1300, actualRezult.getDayPrice());

        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.getTransaction().commit();
    }

    @Test
    void deleteCarCategoryTest() {
        session.beginTransaction();
        session.save(carCategory);
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);

        CarCategory expectedResult = session.get(CarCategory.class, carCategory.getId());
        Assertions.assertNull(expectedResult);
        session.getTransaction().commit();

    }

    @AfterAll
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        log.info("SessionFactory destroyed");
    }

    @AfterEach
    public void closeSession() {

        if (session != null) {
            session.close();
        }

        log.info("Session closed");
    }
}