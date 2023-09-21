package com.avuar1.repository;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CarRepository extends JpaRepository<Car, Integer>, QuerydslPredicateExecutor<Car> {

    List<Car> findByCarCategory(CarCategory carCategory);

}
