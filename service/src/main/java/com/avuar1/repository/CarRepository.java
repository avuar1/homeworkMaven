package com.avuar1.repository;

import com.avuar1.entity.Car;
import com.avuar1.entity.CarModel;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;


public class CarRepository extends RepositoryBase<Integer, Car>{

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }


}
