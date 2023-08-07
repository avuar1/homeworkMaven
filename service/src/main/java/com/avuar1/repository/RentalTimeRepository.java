package com.avuar1.repository;

import com.avuar1.entity.RentalTime;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Component;

@Component
public class RentalTimeRepository extends RepositoryBase<Integer, RentalTime> {

    public RentalTimeRepository(EntityManager entityManager) {
        super(RentalTime.class, entityManager);
    }
}
