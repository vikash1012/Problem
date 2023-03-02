package com.olx.springDemo.service;

import com.olx.springDemo.controller.dto.CarRequest;
import com.olx.springDemo.controller.dto.CarResponse;
import com.olx.springDemo.model.Car;
import com.olx.springDemo.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    CarRepository carRepository;

    CarService carService;

    @BeforeEach
    void setup() {
        carService = new CarService(carRepository);
    }

    @Test
    void ShouldReturnListOfCars() {
        List<CarResponse> expectedCars = List.of(new CarResponse(1L, "Tata", "Nexon", "Red"));
        List<Car> fetchedCars = List.of(new Car(1L, "Tata", "Nexon", "Red"));
        when(carRepository.fetchCars()).thenReturn(fetchedCars);

        List<CarResponse> actualCars = carService.getCars();

        assertEquals(expectedCars, actualCars);
    }

    @Test
    void ShouldReturnCar() throws Exception {
        CarResponse expectedCar = new CarResponse(8L,"Toyota","Fortuner","Black");
        Car foundCar = new Car(8L,"Toyota","Fortuner","Black");
        when(carRepository.findCar("8")).thenReturn(foundCar);

        CarResponse actualCar = carService.getCar("8");

        assertEquals(expectedCar, actualCar);
    }

    @Test
    void ShouldReturnCarId() {
        Long expectedCarId = 8L;
        CarRequest carRequest = new CarRequest("Toyota","Fortuner","Black");
        when(carRepository.createCar(new Car("Toyota","Fortuner","Black"))).thenReturn(8L);

        Long actualCarId = carService.createCar(carRequest);

        assertEquals(expectedCarId, actualCarId);
    }
}
