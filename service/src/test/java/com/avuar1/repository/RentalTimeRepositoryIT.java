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
import com.avuar1.util.TestDataImporter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class RentalTimeRepositoryIT {

    private final RentalTimeRepository rentalTimeRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

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
    void checkDeleteRentalTime() {
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

        rentalTimeRepository.delete(saveRenatlTime);

        RentalTime rentalTime1 = entityManager.find(RentalTime.class, saveRenatlTime.getId());
        assertNull(rentalTime1);
    }

    @Test
    void checkUpdateRentalTime() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        entityManager.persist(carCategory);
        entityManager.persist(user);
        entityManager.persist(car);
        entityManager.persist(order);

        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(LocalDateTime.of(2020, 1, 25, 12, 0))
                .endRentalTime(LocalDateTime.of(2020, 1, 29, 18, 0))
                .order(order)
                .build();

        var saveRenatlTime = rentalTimeRepository.save(rentalTime);
        saveRenatlTime.setStartRentalTime(LocalDateTime.of(2025, 1, 25, 12, 00, 00));
        rentalTimeRepository.update(saveRenatlTime);

        entityManager.flush();
        RentalTime rentalTime1 = entityManager.find(RentalTime.class, saveRenatlTime.getId());
        assertThat(rentalTime1.getStartRentalTime())
                .isEqualTo(LocalDateTime.of(2025, 1, 25, 12, 00, 00));
    }

    @Test
    void checkFindByIdRentalTime() {

        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        User user = createUser();
        Order order = createOrder(user, car);

        entityManager.persist(car);
        entityManager.persist(order);

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
        assertThat(results).hasSize(3);

        var startRentalTime1 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime2 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        var startRentalTime3 = LocalDateTime.of(2023, 10, 07, 12, 00, 00);
        List<LocalDateTime> beginTimes = results.stream().map(RentalTime::getStartRentalTime).collect(toList());
        assertThat(beginTimes).containsExactlyInAnyOrder(startRentalTime1, startRentalTime2, startRentalTime3);
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