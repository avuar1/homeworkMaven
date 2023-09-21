package com.avuar1.mapper;

import com.avuar1.dto.CarCreateEditDto;
import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import com.avuar1.repository.CarCategoryRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarCreateEditMapper implements Mapper<CarCreateEditDto, Car> {

    private final CarCategoryRepository carCategoryRepository;

    @Override
    public Car map(CarCreateEditDto fromObject, Car toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Car map(CarCreateEditDto object) {
        Car car = new Car();
        copy(object, car);
        return car;
    }

    private void copy(CarCreateEditDto object, Car car) {
        car.setCarModel(CarModel.valueOf(object.getModel()));
        car.setColour(object.getColour());
        car.setSeatsQuantity(object.getSeatsQuantity());
        car.setImage(object.getImage());
        car.setCarCategory(getCarCategory(object.getCarCategoryId()));
    }

    public CarCategory getCarCategory(Integer carCategoryId) {
        return Optional.ofNullable(carCategoryId)
                .flatMap(carCategoryRepository::findById)
                .orElse(null);
    }
}
