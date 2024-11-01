package com.johanwatelet.gardeningapp.repositories;

import com.johanwatelet.gardeningapp.bootstrap.BootstrapData;
import com.johanwatelet.gardeningapp.entities.Plant;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Import(BootstrapData.class)
class PlantRepositoryTest {


    @Autowired
    PlantRepository plantRepository;


    @Test
    void findByIdNotFound() {
        Optional<Plant> byId = plantRepository.findById(-1L);

        assertThat(byId).isEmpty();
    }

    @Test
    void deletePlant() {
        Plant plant = plantRepository.findAll().get(0);

        plantRepository.delete(plant);


        assertThat(plantRepository.findAll()).hasSize(2);
    }

    @Test
    @Transactional
    void updatePlant() {
        Plant plant = plantRepository.findAll().get(0);

        String newName = "New Name";

        plant.setName(newName);

        Plant savedPlant = plantRepository.save(plant);

        assertThat(savedPlant.getName()).isEqualTo(newName);
        assertThat(plant.getId()).isEqualTo(savedPlant.getId());
    }


    @Test
    @Transactional
    void createPlant() {

        Plant plant = Plant.builder()
                .name("New Name")
                .description("New Description")
                .build();

        Plant savedPlant = plantRepository.save(plant);


        assertThat(savedPlant.getName()).isEqualTo(plant.getName());
        assertThat(savedPlant.getDescription()).isEqualTo(plant.getDescription());
        assertThat(savedPlant.getId()).isNotNull();
    }

    @Test
    void getPlantById() {
        Plant plant = plantRepository.findAll().get(0);


        Plant result = plantRepository.findById(plant.getId()).get();


        assertThat(result.getId()).isEqualTo(plant.getId());
    }

    @Test
    void getAllPlants() {

        List<Plant> plants = plantRepository.findAll();


        assertThat(plants).isNotEmpty();
    }
}