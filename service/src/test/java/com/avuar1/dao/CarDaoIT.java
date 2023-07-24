package com.avuar1.dao;

import com.avuar1.dto.CarFilter;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import com.avuar1.util.HibernateUtil;
import java.util.List;
import java.util.Optional;
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

class CarDaoIT {

    private static final SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
    private final CarDao carDao = CarDao.getInstance().getInstance();
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
    void findAllCars() {

        List<Car> results = carDao.findAll(session);
        assertThat(results).hasSize(3);
    }

    @Test
    void findAllCarsByCarCategory() {

        List<Car> results = carDao.findAllByCarCategory(session, CategoryLevel.ECONOM);
        assertThat(results).hasSize(3);

    }

    @Test
    void findCarDayPriceByCarModel() {

        Optional<Double> carDayPrice = carDao.findDayPriceByCarModel(session, CarModel.OPEL);

        carDayPrice.ifPresent(value -> assertThat(value).isNotNull());
        carDayPrice.ifPresent(value -> assertThat(value).isEqualTo(1200.00));
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {

        CarFilter filter = CarFilter.builder()
                .colour("White")
                .seatsQuantity(5)
                .build();
        List<Car> cars = carDao.findAllByColourAndSeatsQuantity(
                session, filter);

        assertThat(cars).hasSize(1);

        List<CarModel> modelNames = cars.stream().map(Car::getCarModel).collect(toList());
        assertThat(modelNames).containsExactlyInAnyOrder(CarModel.TOYOTA);

    }


}