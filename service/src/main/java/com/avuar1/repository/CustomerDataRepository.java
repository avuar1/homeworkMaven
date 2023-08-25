package com.avuar1.repository;

import com.avuar1.entity.CustomerData;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class CustomerDataRepository extends RepositoryBase<Integer, CustomerData> {

    public CustomerDataRepository(EntityManager entityManager) {
        super(CustomerData.class, entityManager);
    }
}
