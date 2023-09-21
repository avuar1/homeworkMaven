package com.avuar1.mapper;

import com.avuar1.dto.CarCategoryReadDto;
import com.avuar1.dto.CarReadDto;
import com.avuar1.entity.Car;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarReadMapper implements Mapper<Car, CarReadDto> {

    private final CarCategoryReadMapper carCategoryReadMapper;

    @Override
    public CarReadDto map(Car object) {
        CarCategoryReadDto carCategory = Optional.ofNullable(object.getCarCategory())
                .map(carCategoryReadMapper::map)
                .orElse(null);


        return new CarReadDto(
                object.getId(),
                object.getCarModel().toString(),
                object.getColour(),
                object.getSeatsQuantity(),
                object.getImage(),
                carCategory
        );
    }
}
