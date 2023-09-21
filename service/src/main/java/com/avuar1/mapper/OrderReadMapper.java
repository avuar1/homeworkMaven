package com.avuar1.mapper;

import com.avuar1.dto.CarReadDto;
import com.avuar1.dto.OrderReadDto;
import com.avuar1.dto.UserReadDto;
import com.avuar1.entity.Order;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderReadMapper implements Mapper<Order, OrderReadDto> {

    private final UserReadMapper userReadMapper;
    private final CarReadMapper carReadMapper;

    @Override
    public OrderReadDto map(Order object) {
        UserReadDto user = Optional.ofNullable(object.getUser())
                .map(userReadMapper::map)
                .orElse(null);
        CarReadDto car = Optional.ofNullable(object.getCar())
                .map(carReadMapper::map)
                .orElse(null);

        return new OrderReadDto(
                object.getId(),
                user,
                car,
                object.getOrderStatus(),
                object.getRentalTimes(),
                object.getMessage()
        );
    }
}
