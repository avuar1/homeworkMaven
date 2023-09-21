package com.avuar1.mapper;

import com.avuar1.dto.CarCategoryReadDto;
import com.avuar1.entity.CarCategory;
import org.springframework.stereotype.Component;

@Component
public class CarCategoryReadMapper implements Mapper<CarCategory, CarCategoryReadDto> {

    @Override
    public CarCategoryReadDto map(CarCategory object) {
        return new CarCategoryReadDto(
                object.getId(),
                object.getCategoryLevel(),
                object.getDayPrice()
        );
    }
}
