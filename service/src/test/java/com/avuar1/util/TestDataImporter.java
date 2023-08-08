package com.avuar1.util;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import com.avuar1.entity.CustomerData;
import com.avuar1.entity.Order;
import com.avuar1.entity.OrderStatus;
import com.avuar1.entity.RentalTime;
import com.avuar1.entity.Role;
import com.avuar1.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.EntityManager;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

@UtilityClass
public class TestDataImporter {

    public void importData(EntityManager entityManager) {

        User user1 = saveUser(entityManager, "Maksim", "Petrov", "maks@gmail.com",
                "123456", Role.CLIENT);
        User user2 = saveUser(entityManager, "Aleksey", "Smirnov", "aleks@gmail.com",
                "123456", Role.CLIENT);
        User user3 = saveUser(entityManager, "Sergey", "Petrov", "sergey@gmail.com",
                "123456", Role.CLIENT);

        CustomerData customerData1 = saveCustomerData(entityManager, user1, "22342",
                LocalDate.of(2024, 3, 23), 4500.00);
        CustomerData customerData2 = saveCustomerData(entityManager, user2, "343",
                LocalDate.of(2025, 6, 10), 1000.00);
        CustomerData customerData3 = saveCustomerData(entityManager, user3, "225435442",
                LocalDate.of(2029, 3, 23), 4500.00);


        CarCategory carCategory1 = saveCarCategory(entityManager, CategoryLevel.ECONOM, 1200.00);
        CarCategory carCategory2 = saveCarCategory(entityManager, CategoryLevel.ECONOM, 1200.00);
        CarCategory carCategory3 = saveCarCategory(entityManager, CategoryLevel.ECONOM, 1200.00);

        Car car1 = saveCar(entityManager, CarModel.OPEL, carCategory1, "Red", 5);
        Car car2 = saveCar(entityManager, CarModel.MERSEDES, carCategory2, "Green", 5);
        Car car3 = saveCar(entityManager, CarModel.TOYOTA, carCategory3, "White", 5);

        Order order = saveOrder(entityManager, user1, car1, OrderStatus.ACCEPTED, "wer");
        Order order2 = saveOrder(entityManager, user2, car2, OrderStatus.CANCELED, "ere");
        Order order3 = saveOrder(entityManager, user3, car3, OrderStatus.PROCESSING, "weererr");

        RentalTime rentalTime1 = saveRentalTime(entityManager, car1, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order);
        RentalTime rentalTime2 = saveRentalTime(entityManager, car2, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order2);
        RentalTime rentalTime3 = saveRentalTime(entityManager, car3, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order3);

        // метод для сохранения RentalTime и создать объект Rental Time
        // метод для сохранения user и создания объекта user
        // метод для сохранения customer data и созания обекта customerData


    }

    private CustomerData saveCustomerData(EntityManager entityManager, User user, String driverLicenseNumber,
                                          LocalDate driverLicenseExpiration, Double creditAmount) {
        CustomerData customerData = CustomerData.builder()
                .user(user)
                .driverLicenseNumber(driverLicenseNumber)
                .driverLicenseExpiration(driverLicenseExpiration)
                .creditAmount(creditAmount)
                .build();
        entityManager.persist(customerData);
        return customerData;
    }

    private RentalTime saveRentalTime(EntityManager entityManager, Car car, LocalDateTime startRentalTime,
                                      LocalDateTime endRentalTime, Order order) {
        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(startRentalTime)
                .endRentalTime(endRentalTime)
                .order(order)
                .build();
        entityManager.persist(rentalTime);
        return rentalTime;
    }


    private CarCategory saveCarCategory(EntityManager entityManager, CategoryLevel categoryLevel, Double dayPrice) {
        CarCategory carCategory = CarCategory.builder()
                .categoryLevel(categoryLevel)
                .dayPrice(dayPrice)
                .build();
        entityManager.persist(carCategory);
        return carCategory;
    }


    private Car saveCar(EntityManager entityManager, CarModel carModel, CarCategory carCategory, String color, Integer seatsQuantity) {
        Car car = Car.builder()
                .carModel(carModel) // метод для сохранения car model и создание объекта CarModel
                .carCategory(carCategory) // метод для сохрнаения CarCategory и создание объекта CarCategory
                .colour(color)
                .seatsQuantity(seatsQuantity)
                .build();
        entityManager.persist(car);
        return car;
    }

    private Order saveOrder(EntityManager entityManager, User user, Car car, OrderStatus orderStatus,
                            String message) {
        Order order = Order.builder()
                .user(user)
                .car(car) // Создать метод для сохранения машины
                .orderStatus(orderStatus) // Создать метод для создания orderStays и создать объекты
                .message(message)
                .build();
        entityManager.persist(order);
        return order;
    }

    private User saveUser(EntityManager entityManager, String firstname, String lastname,
                          String email, String password, Role role) {

        User user = User.builder()
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .password(password)
                .role(role)
                .build();
        entityManager.persist(user);
        return user;
    }

}
