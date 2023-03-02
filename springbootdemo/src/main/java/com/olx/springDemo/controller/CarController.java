package com.olx.springDemo.controller;

import com.olx.springDemo.controller.dto.CarRequest;
import com.olx.springDemo.controller.dto.CreateCarResponse;
import com.olx.springDemo.controller.dto.CarResponse;
import com.olx.springDemo.exceptions.CarNotFoundException;
import com.olx.springDemo.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarController {

    CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<CarResponse> getCars() {
        return this.carService.getCars();
    }

    @GetMapping("/cars/{id}")
    public CarResponse getCars(@PathVariable String id) throws CarNotFoundException {
        return this.carService.getCar(id);
    }

    //  Create a car
    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse createCar(@RequestBody CarRequest carRequest) {
        Long carId = this.carService.createCar(carRequest);
        return new CreateCarResponse(carId);
    }
}

// Composition
// Aggregation
// Autowired
// install postgres

/*
TASK

1) Update get cars API to return DTO instead of model.
2) Create a new api to fetch a particular car.
    GET http://localhost:8080/cars/:{id}
    - Response - 200 OK
    {
        "id": 1,
        "make": "Honda",
        "model": "Civic",
        "color": "Blue"
    }
*/
