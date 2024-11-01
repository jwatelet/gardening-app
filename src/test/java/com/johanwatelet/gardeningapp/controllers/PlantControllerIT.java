package com.johanwatelet.gardeningapp.controllers;

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
    private PlantController plantController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void listAllBeers_withPaginationParams() throws Exception {
        mockMvc.perform(get(PlantController.PLANT_PATH)
                        .queryParam("pageNumber", "1")
                        .queryParam("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(1)));
    }

    @Test
    void listAllBeers() throws Exception {
        mockMvc.perform(get(PlantController.PLANT_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", is(3)));

    }
}