package com.avuar1.dto;

import com.avuar1.entity.CategoryLevel;
import lombok.Value;

@Value
public class CarCategoryReadDto {

    Integer id;
    CategoryLevel category;
    Double dayPrice;
}
