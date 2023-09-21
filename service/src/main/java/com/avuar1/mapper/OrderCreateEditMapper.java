package com.avuar1.mapper;

import com.avuar1.dto.OrderCreateEditDto;
import com.avuar1.entity.Car;
import com.avuar1.entity.Order;
import com.avuar1.entity.User;
import com.avuar1.repository.CarRepository;
import com.avuar1.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderCreateEditMapper implements Mapper<OrderCreateEditDto, Order> {

    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public Order map(OrderCreateEditDto fromObject, Order toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    @Override
    public Order map(OrderCreateEditDto object) {
        Order order = new Order();
        copy(object, order);
        return order;
    }

    private void copy(OrderCreateEditDto object, Order order) {
        order.setUser(getUser(object.getUserId()));
        order.setCar(getCar(object.getCarId()));
        order.setBeginTime(object.getBeginTime());
        order.setEndTime(object.getEndTime());
        order.setOrderStatus(object.getStatus());
        order.setMessage(object.getMessage());
    }

    public Car getCar(Integer carId) {
        return Optional.ofNullable(carId)
                .flatMap(carRepository::findById)
                .orElse(null);
    }

    public User getUser(Integer userId) {
        return Optional.ofNullable(userId)
                .flatMap(userRepository::findById)
                .orElse(null);
    }
}
