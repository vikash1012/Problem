package com.olx.springDemo.service;

import com.olx.springDemo.controller.dto.CarRequest;
import com.olx.springDemo.controller.dto.CarResponse;
import com.olx.springDemo.exceptions.CarNotFoundException;
import com.olx.springDemo.model.Car;
import com.olx.springDemo.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {

    CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<CarResponse> getCars() {
        List<CarResponse> listOfGetResponses = new ArrayList<>();
        List<Car> listOfCars = this.carRepository.fetchCars();
        for (Car car : listOfCars) {
            CarResponse cardetails = new CarResponse(car.getId(), car.getMake(), car.getModel(), car.getColor());
            listOfGetResponses.add(cardetails);
        }
        return listOfGetResponses;
    }

    public CarResponse getCar(String CarId) throws CarNotFoundException {
        Car car = this.carRepository.findCar(CarId);
        CarResponse carResponseWithId = new CarResponse(car.getId(), car.getMake(), car.getModel(), car.getColor());
        return carResponseWithId;
    }

    public Long createCar(CarRequest carRequest) {
        Car car = new Car(carRequest.getMake(), carRequest.getModel(), carRequest.getColor());
        return this.carRepository.createCar(car);
    }

}
