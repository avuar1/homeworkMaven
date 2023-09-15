package com.avuar1.repository;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CarModel;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface CarRepository extends JpaRepository<Car, Integer>, QuerydslPredicateExecutor<Car> {

    List<Car> findByCarCategory(CarCategory carCategory);

    @Query("select c.carCategory from Car c where c.carModel = :model")
    Optional<CarCategory> findByModel(String model);

//    @Query(value = "SELECT cc.day_price " +
//            "FROM car_category cc " +
//            "JOIN car c on cc.id = c.category_id" +
//            "WHERE c.car_model = :carModel",
//            nativeQuery = true)
//    Optional<Double> findDayPriceBy(@Param("carModel") CarModel carModel);
}
