package com.avuar1.repository;

import com.avuar1.entity.CarCategory;
import com.avuar1.entity.CategoryLevel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CarCategoryRepository extends JpaRepository<CarCategory, Integer>,
        QuerydslPredicateExecutor<CarCategory> {

    Optional<CarCategory> findByCategoryLevel(CategoryLevel categoryLevel);
}
