package com.avuar1.dto;

import com.avuar1.entity.OrderStatus;
import com.avuar1.entity.RentalTime;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Value;

@Value
public class OrderReadDto {

    Integer id;
    UserReadDto user;
    CarReadDto car;
    OrderStatus orderStatus;
    List<RentalTime> rentalTimes;
    String message;
}
