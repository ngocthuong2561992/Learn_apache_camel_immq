package com.camel.repository;

import com.camel.entity.RentalNewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalNewRepository extends JpaRepository<RentalNewEntity, Integer> {
}
