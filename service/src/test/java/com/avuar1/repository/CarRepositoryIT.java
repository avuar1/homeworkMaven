package com.avuar1.repository;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestDataImporter;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CarRepositoryIT {

    private final static SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory(); // фабрика на все тесты
    private Session session; // Сессия на каждый тест своя


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
    void checkSaveCar() {
        var carRepository = new CarRepository(session);

        CarCategory carCategory = createCarCategory();
        session.save(carCategory);
        session.flush();

        Car car = createCar(carCategory);

        var savedCar = carRepository.save(car);

        assertNotNull(savedCar.getId());

    }

    @Test
    void checkDeleteCar() {
        var carRepository = new CarRepository(session);

        CarCategory carCategory = createCarCategory();
        session.save(carCategory);
        session.flush();

        Car car = createCar(carCategory);

        var savedCar = carRepository.save(car);
        carRepository.delete(savedCar);

        Car car1 = session.get(Car.class, savedCar.getId());

        assertNull(car1);

    }
    @Test
    void checkUpdateCar() {
        var carRepository = new CarRepository(session);

        CarCategory carCategory = createCarCategory();
        session.save(carCategory);
        session.flush();

        Car car = createCar(carCategory);

        var savedCar = carRepository.save(car);

        Car car1 = session.get(Car.class, savedCar.getId());
        car1.setColour("BLUE");
        carRepository.update(car);
        session.flush();

        assertThat(car1.getColour()).isEqualTo("BLUE");

    }

    @Test
    void checkFindByIdCar() {
        var carRepository = new CarRepository(session);
        CarCategory carCategory = createCarCategory();
        session.save(carCategory);
        session.flush();

        Car car = createCar(carCategory);

        var savedCar = carRepository.save(car);

        Optional<Car> car1 = carRepository.findById(savedCar.getId());

        assertThat(car1).isNotNull();
        car1.ifPresent(value -> assertThat(value.getCarModel()).isEqualTo(car.getCarModel()));
        car1.ifPresent(value -> assertThat(value.getColour()).isEqualTo(car.getColour()));
    }

    @Test
    void checkFindAllCars() {
        var carRepository = new CarRepository(session);

        List<Car> results = carRepository.findAll();
        assertThat(results).hasSize(11);

        List<String> cars = results.stream().map(Car::getColour).collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "Red", "white", "Red", "Red");
    }

    private Car createCar(CarCategory carCategory) {
        Car car = Car.builder()
                .carModel(CarModel.OPEL)
                .carCategory(carCategory)
                .colour("Red")
                .seatsQuantity(5)
                .build();
        return car;
    }

    private CarCategory createCarCategory() {
        CarCategory carCategory = CarCategory.builder()
                .categoryLevel(CategoryLevel.ECONOM)
                .dayPrice(1200.00)
                .build();
        return carCategory;
    }

}
