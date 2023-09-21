package com.avuar1.mapper;

import com.avuar1.dto.CarCategoryCreateEditDto;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CategoryLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarCategoryCreateEditMapper implements Mapper<CarCategoryCreateEditDto, CarCategory> {

    @Override
    public CarCategory map(CarCategoryCreateEditDto fromObject, CarCategory toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public CarCategory map(CarCategoryCreateEditDto object) {
        CarCategory carCategory = new CarCategory();
        copy(object, carCategory);
        return carCategory;
    }

    private void copy(CarCategoryCreateEditDto object, CarCategory car) {
        car.setCategoryLevel(CategoryLevel.valueOf(object.getCategory()));
        car.setDayPrice(object.getDayPrice());
    }
}
