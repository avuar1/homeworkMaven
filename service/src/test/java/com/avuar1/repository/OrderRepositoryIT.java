package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import com.avuar1.util.TestDataImporter;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class OrderRepositoryIT {

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
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

        orderRepository.save(order);
        assertNotNull(order);
    }

    @Test
    void checkDeleteOrder() {

        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        Order orderSaved = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();

        orderRepository.save(orderSaved);
        orderRepository.delete(orderSaved);

        Order order1 = entityManager.find(Order.class, orderSaved.getId());

        assertNull(order1);
    }

    @Test
    void checkUpdateOrder() {
        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        Order orderSaved = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();

        orderRepository.save(orderSaved);

        Order order1 = entityManager.find(Order.class, orderSaved.getId());
        order1.setMessage("Test");
        orderRepository.update(order1);

        entityManager.flush();
        Order order2 = entityManager.find(Order.class, orderSaved.getId());

        assertThat(order2.getMessage()).isEqualTo("Test");
    }

    @Test
    void checkFindByIdOrder() {
        User user = createUser();
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        Order orderSaved = Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();

        orderRepository.save(orderSaved);

        Optional<Order> order1 = orderRepository.findById(orderSaved.getId());

        assertThat(order1).isNotNull();
        order1.ifPresent(value -> assertThat(value.getMessage()).isEqualTo("wer"));
    }

    @Test
    void checkFindAllOrders() {
        List<Order> results = orderRepository.findAll();
        assertThat(results).hasSize(3);
    }

    private User createUser() {
        return User.builder()
                .firstName("Maksim")
                .lastName("Petrov")
                .email("uniq@gmail.com")
                .password("123456")
                .role(Role.CLIENT)
                .build();
    }

    @Test
    void findOrdersByStatus() {
        List<Order> results = orderRepository.findByStatus(OrderStatus.ACCEPTED);

        assertThat(results).hasSize(1);
    }

    private Car createCar(CarCategory carCategory) {
        return Car.builder()
                .carModel(CarModel.OPEL)
                .carCategory(carCategory)
                .colour("Red")
                .seatsQuantity(5)
                .build();
    }

    private CarCategory createCarCategory() {
        return CarCategory.builder()
                .categoryLevel(CategoryLevel.ECONOM)
                .dayPrice(1200.00)
                .build();
    }
}