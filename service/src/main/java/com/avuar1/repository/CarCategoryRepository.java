package com.avuar1.repository;

import com.avuar1.entity.CarCategory;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public class CarCategoryRepository extends RepositoryBase<Integer, CarCategory> {

    public CarCategoryRepository(EntityManager entityManager) {
        super(CarCategory.class, entityManager);
    }
}
