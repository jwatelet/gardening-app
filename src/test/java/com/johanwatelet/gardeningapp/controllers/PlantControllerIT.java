package com.johanwatelet.gardeningapp.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.johanwatelet.gardeningapp.entities.Plant;
import com.johanwatelet.gardeningapp.model.PlantDTO;
import com.johanwatelet.gardeningapp.repositories.PlantRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlantControllerIT {

    @Autowired
    PlantRepository plantRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    void deletePlantByIdNotFound() throws Exception {
        mockMvc.perform(delete(PlantController.PLANT_PATH_ID, -1L))
                .andExpect(status().isNotFound());


        assertThat(plantRepository.findAll()).hasSize(3);
    }

    @Test
    @Transactional
    void deletePlantById() throws Exception {
        Plant plant = plantRepository.findAll().get(0);

        mockMvc.perform(delete(PlantController.PLANT_PATH_ID, plant.getId()))
                .andExpect(status().isNoContent());

        assertThat(plantRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    void updatePlantById_withEmptyName() throws Exception {
        Plant plant = plantRepository.findAll().get(0);

        final String updatedName = "";
        final String updatedDescription = "Updated description";

        plant.setName(updatedName);
        plant.setDescription(updatedDescription);

        mockMvc.perform(put(PlantController.PLANT_PATH_ID, plant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plant)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updatePlantByIdNotFound() throws Exception {
        Plant plant = plantRepository.findAll().get(0);

        final String updatedName = "Updated name";
        final String updatedDescription = "Updated description";

        plant.setName(updatedName);
        plant.setDescription(updatedDescription);

        mockMvc.perform(put(PlantController.PLANT_PATH_ID, -1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plant)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updatePlantById() throws Exception {

        Plant plant = plantRepository.findAll().get(0);

        final String updatedName = "Updated name";
        final String updatedDescription = "Updated description";

        plant.setName(updatedName);
        plant.setDescription(updatedDescription);

        mockMvc.perform(put(PlantController.PLANT_PATH_ID, plant.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plant)))
                .andExpect(jsonPath("$.name", is(updatedName)))
                .andExpect(jsonPath("$.description", is(updatedDescription)))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    void createNewPlant_withInvalidName() throws Exception {
        PlantDTO plantDTO = PlantDTO.builder()
                .description("New description")
                .build();

        mockMvc.perform(post(PlantController.PLANT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void createNewPlant() throws Exception {

        PlantDTO plantDTO = PlantDTO.builder()
                .name("New plant")
                .description("New description")
                .build();

        mockMvc.perform(post(PlantController.PLANT_PATH)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(plantDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.name", is(plantDTO.getName())))
                .andExpect(jsonPath("$.description", is(plantDTO.getDescription())))
                .andExpect(jsonPath("$.id").value(notNullValue()));
    }

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