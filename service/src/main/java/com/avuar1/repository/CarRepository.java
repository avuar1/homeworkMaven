package com.avuar1.repository;

import com.avuar1.entity.Car;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;


@Component
public class CarRepository extends RepositoryBase<Integer, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

}
