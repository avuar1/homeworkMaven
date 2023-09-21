package com.avuar1.repository;

import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CategoryLevel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer>,
        QuerydslPredicateExecutor<CarCategory> {

    Optional<CarCategory> findByCategoryLevel(CategoryLevel categoryLevel);

    @Query("select max (cc.dayPrice) from CarCategory cc join cc.cars c where c.carModel = :model")
    Optional<Double> findDayPriceByCarModel(String model);

    @Query("select c.carCategory from Car c where c.carModel = :model")
    Optional<CarCategory> findByModel(String model);
}
