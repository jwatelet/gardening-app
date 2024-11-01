package com.johanwatelet.gardeningapp.services;

import com.johanwatelet.gardeningapp.model.PlantDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PlantService {

    Page<PlantDTO> listPlants(Integer pageNumber, Integer pageSize);

    Optional<PlantDTO> getPlantById(Long plantId);

    PlantDTO createPlant(PlantDTO plantDTO);
}
