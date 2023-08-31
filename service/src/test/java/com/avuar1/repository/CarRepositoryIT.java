package com.avuar1.repository;

import com.avuar1.annotation.IT;
import com.avuar1.dao.QPredicate;
import com.avuar1.dto.CarFilter;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.entity.CategoryLevel;
import static com.avuar1.entity.QCar.car;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@IT
@Sql({
        "classpath:sql/data.sql"
})
@RequiredArgsConstructor
class CarRepositoryIT {

    private static final int ID_FIRST = 1;

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;

    @Test
    void checkSaveCar() {
        Car car = Car.builder()
                .carModel(CarModel.LAMBORGINI)
                .colour("GREEN")
                .seatsQuantity(5)
                .build();

        carRepository.save(car);

        assertNotNull(car.getId());
    }

    @Test
    void checkUpdateCar() {
        CarCategory carCategory = createCarCategory();
        Car carForSave = createCar(carCategory);
        carCategoryRepository.saveAndFlush(carCategory);

        var savedCar = carRepository.saveAndFlush(carForSave);
        savedCar.setColour("BLUE");
        carRepository.saveAndFlush(savedCar);

        Optional<Car> car = carRepository.findById(savedCar.getId());
        car.ifPresent(value -> assertThat(value.getColour()).isEqualTo("BLUE"));
    }

    @Test
    void checkDeleteCar() {
        CarCategory carCategory = createCarCategory();
        Car car = createCar(carCategory);
        carCategoryRepository.saveAndFlush(carCategory);

        carRepository.saveAndFlush(car);

        carRepository.delete(car);

        Optional<Car> deletedCar = carRepository.findById(car.getId());
        assertTrue(deletedCar.isEmpty());
    }

    @Test
    void checkFindByIdCar() {
        Optional<Car> car = carRepository.findById(ID_FIRST);

        assertThat(car).isNotNull();
        car.ifPresent(value -> assertThat(value.getCarModel()).isEqualTo(CarModel.OPEL));
        car.ifPresent(value -> assertThat(value.getColour()).isEqualTo("Red"));
    }

    @Test
    void checkFindAllCars() {

        List<Car> results = carRepository.findAll();
        assertThat(results).hasSize(6);

        List<String> cars = results.stream()
                .map(Car::getCarModel)
                .map(CarModel::toString)
                .collect(toList());
        assertThat(cars).containsExactlyInAnyOrder(
                "OPEL", "MERSEDES", "TOYOTA", "LAMBORGINI", "SHKODA", "CHERRY");
    }

    @Test
    void findAllCarsByCarCategory() {
        Optional<CarCategory> largeSuv = carCategoryRepository.findByCategoryLevel(CategoryLevel.ECONOM);
        List<Car> results = carRepository.findByCarCategory(largeSuv.orElseThrow());
        assertThat(results).hasSize(1);
    }

    @Test
    void findAllCarsByColourAndSeatsQuantity() {
        CarFilter filter = CarFilter.builder()
                .colour("WHITE")
                .seatsQuantity(7)
                .build();
        var predicate = QPredicate.builder()
                .add(filter.getColour(), car.colour::eq)
                .add(filter.getSeatsQuantity(), car.seatsQuantity::eq)
                .buildAnd();

        List<Car> cars = (List<Car>) carRepository.findAll(predicate);

        assertThat(cars).hasSize(2);

        List<String> modelNames = cars.stream()
                .map(car -> car.getCarModel().name())
                .collect(Collectors.toList());
        assertThat(modelNames).containsExactlyInAnyOrder("TOYOTA", "LAMBORGINI");
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