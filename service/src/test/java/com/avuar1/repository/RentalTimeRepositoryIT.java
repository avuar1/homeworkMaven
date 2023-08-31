package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import com.avuar1.entity.RentalTime;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql({
        "classpath:sql/data.sql"
})
@RequiredArgsConstructor
class RentalTimeRepositoryIT {

    private final RentalTimeRepository rentalTimeRepository;
    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Test
    void checkSaveRentalTime() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .order(order)
                .build();

        var saveRenatlTime = rentalTimeRepository.save(rentalTime);

        assertNotNull(saveRenatlTime.getId());
    }

    @Test
    void checkUpdateRentalTime() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        carCategoryRepository.save(carCategory);
        userRepository.save(user);
        carRepository.save(car);
        orderRepository.save(order);

        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .order(order)
                .build();

        var saveRenatlTime = rentalTimeRepository.save(rentalTime);
        saveRenatlTime.setStartRentalTime(LocalDateTime.of(2025, 1, 25, 12, 00, 00));

        rentalTimeRepository.flush();
        Optional<RentalTime> rentalTime1 = rentalTimeRepository.findById(rentalTime.getId());
        rentalTime1.ifPresent(time -> assertThat(time.getStartRentalTime())
                .isEqualTo(LocalDateTime.of(2025, 1, 25, 12, 00, 00)));
    }

    @Test
    void checkDeleteRentalTime() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        carCategoryRepository.save(carCategory);
        userRepository.save(user);
        carRepository.save(car);
        orderRepository.save(order);

        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .order(order)
                .build();

        var saveRenatlTime = rentalTimeRepository.save(rentalTime);

        rentalTimeRepository.delete(saveRenatlTime);

        Optional<RentalTime> rentalTime1 = rentalTimeRepository.findById(rentalTime.getId());
        assertTrue(rentalTime1.isEmpty());
    }


    @Test
    void checkFindByIdRentalTime() {

        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        carCategoryRepository.save(carCategory);
        userRepository.save(user);
        carRepository.save(car);
        orderRepository.save(order);

        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .order(order)
                .build();

        var saveRenatlTime = rentalTimeRepository.save(rentalTime);

        var beginTime = LocalDateTime.of(2020, 1, 25, 12, 00, 00);
        var endTime = LocalDateTime.of(2020, 1, 29, 18, 00, 00);

        Optional<RentalTime> rentalTime2 = rentalTimeRepository.findById(saveRenatlTime.getId());
        rentalTime2.ifPresent(System.out::println);

        assertThat(rentalTime2).isNotNull();
        rentalTime2.ifPresent(value -> assertThat(value.getStartRentalTime()).isEqualTo(beginTime));
        rentalTime2.ifPresent(value -> assertThat(value.getEndRentalTime()).isEqualTo(endTime));
    }

    @Test
    void checkFindAllRentalTimes() {
        List<RentalTime> results = rentalTimeRepository.findAll();
        assertThat(results).hasSize(2);

        var startRentalTime1 = LocalDateTime.of(2020, 01, 25, 12, 00, 00);
        var startRentalTime2 = LocalDateTime.of(2020, 02, 25, 12, 00, 00);
        List<LocalDateTime> beginTimes = results.stream().map(RentalTime::getStartRentalTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(startRentalTime1, startRentalTime2);
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

    private CarCategory createCarCategory() {
        return CarCategory.builder()
                .categoryLevel(CategoryLevel.ECONOM)
                .dayPrice(1200.00)
                .build();
    }

    private Car createCar(CarCategory carCategory) {
        return Car.builder()
                .carModel(CarModel.OPEL)
                .carCategory(carCategory)
                .colour("Red")
                .seatsQuantity(5)
                .build();
    }

    private Order createOrder(User user, Car car) {
        return Order.builder()
                .user(user)
                .car(car)
                .orderStatus(OrderStatus.ACCEPTED)
                .message("wer")
                .build();
    }

}