package util;


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
import lombok.experimental.UtilityClass;
import org.hibernate.Session;

@UtilityClass
public class TestDataImporter {

    public void importData(Session session) {

        User user1 = saveUser(session, "Maksim", "Petrov", "maks@gmail.com",
                "123456", Role.CLIENT);
        User user2 = saveUser(session, "Aleksey", "Smirnov", "aleks@gmail.com",
                "123456", Role.CLIENT);
        User user3 = saveUser(session, "Sergey", "Petrov", "sergey@gmail.com",
                "123456", Role.CLIENT);

        CustomerData customerData1 = saveCustomerData(session, user1, "22342",
                LocalDate.of(2024, 3, 23), 4500.00);
        CustomerData customerData2 = saveCustomerData(session, user2, "343",
                LocalDate.of(2025, 6, 10), 1000.00);
        CustomerData customerData3 = saveCustomerData(session, user3, "225435442",
                LocalDate.of(2029, 3, 23), 4500.00);


        CarCategory carCategory1 = saveCarCategory(session, CategoryLevel.ECONOM, 1200.00);
        CarCategory carCategory2 = saveCarCategory(session, CategoryLevel.ECONOM, 1200.00);
        CarCategory carCategory3 = saveCarCategory(session, CategoryLevel.ECONOM, 1200.00);

        Car car1 = saveCar(session, CarModel.OPEL, carCategory1, "Red", 5);
        Car car2 = saveCar(session, CarModel.MERSEDES, carCategory2, "Green", 5);
        Car car3 = saveCar(session, CarModel.TOYOTA, carCategory3, "White", 5);

        Order order = saveOrder(session, user1, car1, OrderStatus.ACCEPTED, "wer");
        Order order2 = saveOrder(session, user2, car2, OrderStatus.CANCELED, "ere");
        Order order3 = saveOrder(session, user3, car3, OrderStatus.PROCESSING, "weererr");

        RentalTime rentalTime1 = saveRentalTime(session, car1, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order);
        RentalTime rentalTime2 = saveRentalTime(session, car2, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order2);
        RentalTime rentalTime3 = saveRentalTime(session, car3, LocalDateTime.parse("2023-10-07T12:00:00"),
                LocalDateTime.parse("2023-11-07T12:00:00"), order3);

        // метод для сохранения RentalTime и создать объект Rental Time
        // метод для сохранения user и создания объекта user
        // метод для сохранения customer data и созания обекта customerData


    }

    private CustomerData saveCustomerData(Session session, User user, String driverLicenseNumber,
                                          LocalDate driverLicenseExpiration, Double creditAmount){
        CustomerData customerData = CustomerData.builder()
                .user(user)
                .driverLicenseNumber(driverLicenseNumber)
                .driverLicenseExpiration(driverLicenseExpiration)
                .creditAmount(creditAmount)
                .build();
        session.save(customerData);
        return customerData;
    }

    private RentalTime saveRentalTime(Session session, Car car, LocalDateTime startRentalTime,
                                      LocalDateTime endRentalTime, Order order) {
        RentalTime rentalTime = RentalTime.builder()
                .car(car)
                .startRentalTime(startRentalTime)
                .endRentalTime(endRentalTime)
                .order(order)
                .build();
        session.save(rentalTime);
        return rentalTime;
    }


    private CarCategory saveCarCategory(Session session, CategoryLevel categoryLevel, Double dayPrice) {
        CarCategory carCategory = CarCategory.builder()
                .categoryLevel(categoryLevel)
                .dayPrice(dayPrice)
                .build();
        session.save(carCategory);
        return carCategory;
    }


    private Car saveCar(Session session, CarModel carModel, CarCategory carCategory, String color, Integer seatsQuantity) {
        Car car = Car.builder()
                .carModel(carModel) // метод для сохранения car model и создание объекта CarModel
                .carCategory(carCategory) // метод для сохрнаения CarCategory и создание объекта CarCategory
                .colour(color)
                .seatsQuantity(seatsQuantity)
                .build();
        session.save(car);
        return car;
    }

    private Order saveOrder(Session session, User user, Car car, OrderStatus orderStatus,
                            String message) {
        Order order = Order.builder()
                .user(user)
                .car(car) // Создать метод для сохранения машины
                .orderStatus(orderStatus) // Создать метод для создания orderStays и создать объекты
                .message(message)
                .build();
        session.save(order);
        return order;
    }

    private User saveUser(Session session, String firstname, String lastname,
                          String email, String password, Role role) {

        User user = User.builder()
                .firstName(firstname)
                .lastName(lastname)
                .email(email)
                .password(password)
                .role(role)
                .build();
        session.save(user);
        return user;
    }

}
