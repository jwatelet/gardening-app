package com.johanwatelet.gardeningapp.controllers;

import com.johanwatelet.gardeningapp.entities.Plant;
import com.johanwatelet.gardeningapp.repositories.PlantRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PlantControllerIT {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void getPlantByIdNotFound() throws Exception {

        mockMvc.perform(get(PlantController.PLANT_PATH_ID, 10L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPlantById() throws Exception {

        Plant plant = plantRepository.findAll().get(0);

        mockMvc.perform(get(PlantController.PLANT_PATH_ID, plant.getId()))
                .andExpect(jsonPath("$.id", is(plant.getId().intValue())))
                .andExpect(status().isOk());
    }

    @Test
    void listAllBeers_withPaginationParams() throws Exception {
        mockMvc.perform(get(PlantController.PLANT_PATH)
                        .queryParam("pageNumber", "0")
                        .queryParam("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.page.number", is(0)))
                .andExpect(jsonPath("$.page.totalPages", is(3)))
                .andExpect(jsonPath("$.page.totalElements", is(3)))
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void listAllBeers() throws Exception {
        mockMvc.perform(get(PlantController.PLANT_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(3)));
    }
}