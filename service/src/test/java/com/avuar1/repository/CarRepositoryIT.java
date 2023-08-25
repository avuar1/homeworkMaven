package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.dto.CarFilter;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import com.avuar1.util.TestDataImporter;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@IT
@RequiredArgsConstructor
class CarRepositoryIT {

    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @BeforeEach
    void initDb() {
        TestDataImporter.importData(entityManager);
    }

    @Test
    void checkSaveCar() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);

        var savedCar = carRepository.save(car);

        assertNotNull(savedCar.getId());
    }

    @Test
    void checkDeleteCar() {
        CarCategory carCategory = createCarCategory();
        Car carForSave = createCar(carCategory);

        var savedCar = carRepository.save(carForSave);

        carRepository.delete(savedCar);

        Car car = entityManager.find(Car.class, savedCar.getId());
        assertNull(car);
    }

    @Test
    void checkUpdateCar() {
        CarCategory carCategory = createCarCategory();
        Car carForSave = createCar(carCategory);
        entityManager.persist(carCategory);


        var savedCar = carRepository.save(carForSave);
        savedCar.setColour("BLUE");
        carRepository.update(savedCar);

        entityManager.flush();
        Car car = entityManager.find(Car.class, savedCar.getId());
        assertThat(car.getColour()).isEqualTo("BLUE");
    }

    @Test
    void checkFindByIdCar() {

        CarCategory carCategory = createCarCategory();
        Car carForSave = createCar(carCategory);

        var savedCar = carRepository.save(carForSave);

        Optional<Car> car = carRepository.findById(savedCar.getId());

        assertThat(car).isNotNull();
        car.ifPresent(value -> assertThat(value.getCarModel()).isEqualTo(car.get().getCarModel()));
        car.ifPresent(value -> assertThat(value.getColour()).isEqualTo(car.get().getColour()));
    }

    @Test
    void checkFindAllCars() {
        List<Car> results = carRepository.findAll();
        assertThat(results).hasSize(3);

        List<String> cars = results.stream().map(Car::getColour).collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "Red", "Green", "White");
    }

    @Test
    void findAllCarsByCarCategory() {
        List<Car> results = carRepository.findAllByCarCategory("ECONOM");
        assertThat(results).hasSize(3);
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        CarFilter filter = CarFilter.builder()
                .colour("Red")
                .seatsQuantity(5)
                .build();
        List<Car> cars = carRepository.findAllByColourAndSeatsQuantity(filter);

        assertThat(cars).hasSize(1);

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