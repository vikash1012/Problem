package com.olx.springDemo.repository;

import com.olx.springDemo.model.Car;
import net.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarRepositoryTest {

    CarRepository carRepository;

    @Mock
    JPACarRepository jpaCarRepository;

    @BeforeEach
    void setup() {
        carRepository = new CarRepository(jpaCarRepository);
    }

    @Test
    void ShouldReturnListOfCarsFromDB() {
        Car car = new Car(1L, "Tata", "Nexon", "Red");
        car.getColor();
        List<Car> expectedCars = List.of(car);
        when(jpaCarRepository.findAll()).thenReturn(List.of(car));

        List<Car> actualCars = carRepository.fetchCars();

        assertEquals(expectedCars, actualCars);
    }

    @Test
    void ShouldReturnCarIdFromDB() {
        Long expectedCarId = 4L;
        Car car = new Car(4L, "Toyota", "Fortuner", "Black");
        when(jpaCarRepository.save(car)).thenReturn(car);

        Long actualCarId = carRepository.createCar(car);

        assertEquals(expectedCarId, actualCarId);
    }

    @Test
    void ShouldReturnCarFromDbforGivenValidId() {
        Long id = 1L;
        Car expected=new Car(1L, "Tata", "Punch", "Black");
        when(jpaCarRepository.findById(id)).thenReturn (Optional.of(expected));

        Optional<Car> actual= this.jpaCarRepository.findById(1L);

        assertEquals(expected,actual.get());
    }
    @Test
    void ShouldThrowExceptionforInValidId () throws Exception{
        Optional<Car> car = Optional.empty();
        when(jpaCarRepository.findById(3L)).thenReturn (car);
        String expected="not found";


//        Optional<Car> actual= this.jpaCarRepository.findById(3L);
        Exception actualError=Assertions.assertThrows(Exception.class, ()->carRepository.findCar("3"));

        assertEquals(expected,actualError.getMessage());

    }
}
