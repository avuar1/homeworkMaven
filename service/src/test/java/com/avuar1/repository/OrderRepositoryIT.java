package com.avuar1.repository;

import com.avuar1.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateTestUtil;
import util.TestDataImporter;

import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


class OrderRepositoryIT {

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
    void checkSaveOrder() {

        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);

        Order order = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();


        var orderRepository = new OrderRepository(session);
        session.save(user);
        session.save(carCategory);
        session.save(car);

        orderRepository.save(order);

        session.flush();

        assertNotNull(order);
    }

    @Test
    void checkDeleteOrder() {
        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);

        Order order = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();


        var orderRepository = new OrderRepository(session);
        session.save(user);
        session.save(carCategory);
        session.save(car);

        orderRepository.save(order);
        session.flush();

        orderRepository.delete(order);
         Order order1 = session.get(Order.class, order.getId());

        assertNull(order1);
    }

    @Test
    void checkUpdateOrder() {
        var orderRepository = new OrderRepository(session);

        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);

        Order order = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();

        session.save(user);
        session.save(carCategory);
        session.save(car);

        orderRepository.save(order);
        session.flush();

        order.setMessage("Test");
        orderRepository.update(order);
        session.flush();
        Order order1 = session.get(Order.class, order.getId());
        assertThat(order1.getMessage())
                .isEqualTo("Test");
    }

    @Test
    void checkFindByIdOrder() {
        var orderRepository = new OrderRepository(session);
        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);

        Order order = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();

        session.save(user);
        session.save(carCategory);
        session.save(car);

        orderRepository.save(order);
        session.flush();

        Optional<Order> order1 = orderRepository.findById(order.getId());

        assertThat(order1).isNotNull();

    }

    private User createUser() {
        User user = User.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .email("uniq@gmail.com")
                .password("123456")
                .role(Role.CLIENT)
                .build();
        return user;
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