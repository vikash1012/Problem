package com.olx.springDemo.repository;

import com.olx.springDemo.exceptions.CarNotFoundException;
import com.olx.springDemo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {

    JPACarRepository jpaCarRepository;

    @Autowired
    public CarRepository(JPACarRepository jpaCarRepository) {
        this.jpaCarRepository = jpaCarRepository;
    }

    public List<Car> fetchCars() {
        return this.jpaCarRepository.findAll();
    }


    public Car findCar(String id) throws CarNotFoundException {
        Long carId = Long.parseLong(id);
        Optional<Car> optionalCar = this.jpaCarRepository.findById(carId);
        if (optionalCar.isEmpty()) {
            throw new CarNotFoundException("car not found for Id - " + id);
        }
        return optionalCar.get();
    }

    public Long createCar(Car car) {
        Car savedCar = this.jpaCarRepository.save(car);
        return savedCar.getId();
    }
}
