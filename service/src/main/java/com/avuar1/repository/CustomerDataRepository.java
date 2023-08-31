package com.avuar1.repository;

import com.avuar1.entity.CustomerData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDataRepository extends JpaRepository<CustomerData, Integer> {
}
