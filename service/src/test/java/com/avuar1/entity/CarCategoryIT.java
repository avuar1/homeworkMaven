package com.avuar1.entity;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class CarCategoryIT {

    private static SessionFactory sessionFactory;
    private static Session session;

    @BeforeAll
    static void setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    public void openSession() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @Test
    void createCarCategoryTest() {
        CarCategory carCategory = createCarCategory(); // создается в методе

        session.save(carCategory);
        session.evict(carCategory);
        session.flush();

        CarCategory actualRezult = session.get(CarCategory.class, carCategory.getId());

        assertNotNull(actualRezult.getId());
        assertEquals(actualRezult.getId(), carCategory.getId());
        assertEquals("PREMIUM", actualRezult.getCategoryLevel().toString());

    }

    @Test
    void getCarCategoryTest() {
        CarCategory carCategory = createCarCategory();

        session.save(carCategory);
        session.evict(carCategory);
        session.flush();

        CarCategory actualRezult = session.get(CarCategory.class, carCategory.getId());

        assertNotNull(actualRezult);
        assertEquals(actualRezult.getId(), carCategory.getId());
        assertEquals(actualRezult.getCategoryLevel(), carCategory.getCategoryLevel());
    }

    @Test
    void updateCarCategoryTest() {
        CarCategory carCategory = createCarCategory();
        session.save(carCategory);

        CarCategory savedCarCategory = session.get(CarCategory.class, carCategory.getId());
        savedCarCategory.setDayPrice(1300.00);
        session.update(savedCarCategory);
        session.flush();
        session.clear();

        CarCategory actualRezult = session.get(CarCategory.class, savedCarCategory.getId());

        assertEquals(1300.00, actualRezult.getDayPrice(), 0.01);

    }
    //Правильно ли сделано
    @Test
    void deleteCarCategoryTest() {
        CarCategory carCategory = createCarCategory();
        session.save(carCategory);
        session.flush();
        session.clear();

        session.delete(carCategory);

        CarCategory expectedResult = session.get(CarCategory.class, carCategory.getId());
        assertNull(expectedResult);

    }

    @AfterEach
    public void closeSession() {
        session.getTransaction().rollback();
        if (session != null) {
            session.close();
        }
    }

    @AfterAll
    static void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public CarCategory createCarCategory() {
        return CarCategory.builder()
                .categoryLevel(CategoryLevel.PREMIUM)
                .dayPrice(1500.00)
                .cars(new ArrayList<>())
                .build();
    }
}