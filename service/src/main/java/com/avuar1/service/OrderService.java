package com.avuar1.service;

import com.avuar1.dto.OrderCreateEditDto;
import com.avuar1.dto.OrderReadDto;
import com.avuar1.mapper.OrderCreateEditMapper;
import com.avuar1.mapper.OrderReadMapper;
import com.avuar1.repository.OrderRepository;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.toList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderReadMapper orderReadMapper;
    private final OrderCreateEditMapper orderCreateEditMapper;

    public List<OrderReadDto> findAll() {
        return orderRepository.findAll().stream()
                .map(orderReadMapper::map)
                .collect(toList());
    }

    public Optional<OrderReadDto> findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderReadMapper::map);
    }

    @Transactional
    public OrderReadDto create(OrderCreateEditDto orderDto) {
        return Optional.of(orderDto)
                .map(orderCreateEditMapper::map)
                .map(orderRepository::save)
                .map(orderReadMapper::map)
                .orElseThrow();
    }

    @Transactional
    public Optional<OrderReadDto> update(Integer id, OrderCreateEditDto orderDto) {
        return orderRepository.findById(id)
                .map(entity -> orderCreateEditMapper.map(orderDto, entity))
                .map(orderRepository::saveAndFlush)
                .map(orderReadMapper::map);
    }

    @Transactional
    public boolean delete(Integer id) {
        return orderRepository.findById(id)
                .map(entity -> {
                    orderRepository.delete(entity);
                    orderRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
