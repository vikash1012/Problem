package com.olx.springDemo.repository;

import com.olx.springDemo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JPACarRepository extends JpaRepository<Car, Long> {
}
