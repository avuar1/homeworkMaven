package com.avuar1.dto;

import lombok.Value;

@Value
public class CarReadDto {

    Integer id;
    String model;
    String colour;
    Integer seatsQuantity;
    String image;
    CarCategoryReadDto carCategory;
}
