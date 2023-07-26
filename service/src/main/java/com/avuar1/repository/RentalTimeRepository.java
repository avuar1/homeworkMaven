package com.avuar1.repository;

import com.avuar1.entity.RentalTime;
import javax.persistence.EntityManager;

public class RentalTimeRepository extends RepositoryBase<Integer, RentalTime> {

    public RentalTimeRepository(EntityManager entityManager) {
        super(RentalTime.class, entityManager);
    }
}
