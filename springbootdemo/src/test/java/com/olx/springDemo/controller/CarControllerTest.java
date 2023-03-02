package com.olx.springDemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.olx.springDemo.controller.dto.CarRequest;
import com.olx.springDemo.controller.dto.CarResponse;
import com.olx.springDemo.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@MockBean({CarService.class})
@WebMvcTest(controllers = CarController.class)
class CarControllerTest {
    @MockBean
    CarService carService;

    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    public void init() {
        CarController carController = new CarController(carService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }

    @Test
    void ShouldFetchListOfCarsFromGetCarsApi() throws Exception {
        when(carService.getCars()).thenReturn(List.of(new CarResponse(1L, "tata", "nexon", "red")));
        String expectedResponse = "[{\"id\":1,\"make\":\"tata\",\"model\":\"nexon\",\"color\":\"red\"}]";

        MockHttpServletRequestBuilder request = get("/cars");

        this.mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }

    @Test
    void ShouldFetchListOfCarFromGetApiForParticularId() throws Exception {
        when(carService.getCar("1")).thenReturn(new CarResponse(1L, "tata", "nexon", "red"));
        String expectedResponse = "{\"id\":1,\"make\":\"tata\",\"model\":\"nexon\",\"color\":\"red\"}";

        MockHttpServletRequestBuilder requestWithId = get("/cars/1");

        this.mockMvc.perform(requestWithId)
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));

    }
    @Test
    void ShouldReturnCreateCarResponse() throws Exception {
        when (carService.createCar(new CarRequest("Tata","Nexon","Black"))).thenReturn (5L);
        String expectedResponse="{\"id\":5}";

        MockHttpServletRequestBuilder postRequest = post("/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"make\": \"Tata\",\"model\": \"Nexon\", \"color\":\"Black\"}");

        this.mockMvc.perform(postRequest)
                .andExpect(status().isCreated())
                .andExpect(content().string(expectedResponse));


    }
}
