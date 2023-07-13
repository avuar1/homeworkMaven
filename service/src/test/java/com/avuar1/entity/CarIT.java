package com.avuar1.entity;

import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import util.HibernateTestUtil;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CarIT {

    private static SessionFactory sessionFactory;
    private Session session;
    private CarCategory carCategory;
    private Car car;

    @BeforeAll
    void setup() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    public void  openSession(){
        session = sessionFactory.openSession();


        carCategory = CarCategory.builder()
                .categoryLevel(CategoryLevel.PREMIUM)
                .dayPrice(1500.00)
                .cars(new ArrayList<>())
                .build();

        car = Car.builder()
                .carModel(CarModel.MERSEDES)
                .carCategory(carCategory)
                .colour("Red")
                .seatsQuantity(5)
                .build();
    }

    @Test
    void createCarTest() {

        session.beginTransaction();
        session.save(carCategory);
        session.save(car);

        Car savedCar = session.get(Car.class, car.getId());

        assertNotNull(savedCar.getId());
        assertNotNull(carCategory.getId());
        assertEquals(carCategory.getId(), car.getCarCategory().getId());
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.delete(car);
        session.getTransaction().commit();
    }

    @Test
    void getCarTest(){
        session.beginTransaction();
        session.save(carCategory);
        session.save(car);

        session.getTransaction().commit();

        session.beginTransaction();
        Car foundCar = session.get(Car.class, car.getId());

        assertNotNull(foundCar);
        assertEquals(foundCar, car);

        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.delete(car);
        session.getTransaction().commit();

    }

    @Test
    void updateCarTest() {

        session.beginTransaction();
        session.save(carCategory);
        session.save(car);

        car.setColour("white");

        session.update(car);

        Car expectedCar = session.get(Car.class, car.getId());

        assertEquals("white", expectedCar.getColour());
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.delete(car);
        session.getTransaction().commit();

    }

    @Test
    void deleteCarTest() {

        session.beginTransaction();
        session.save(carCategory);
        session.save(car);
        session.getTransaction().commit();

        session.beginTransaction();
        session.delete(carCategory);
        session.delete(car);

        Car deletedCar = session.get(Car.class, car.getId());
        Assertions.assertNull(deletedCar);
        session.getTransaction().commit();
    }

    @AfterAll
    void tearDown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    @AfterEach
    public void closeSession(){

       if (session != null) {
            session.close();
        }
    }

}