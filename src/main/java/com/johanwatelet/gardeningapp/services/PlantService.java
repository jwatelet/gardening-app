package com.johanwatelet.gardeningapp.services;

import com.johanwatelet.gardeningapp.model.PlantDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface PlantService {

    Page<PlantDTO> listPlants(Integer pageNumber, Integer pageSize);

    Optional<PlantDTO> getById(Long plantId);

    PlantDTO create(PlantDTO plantDTO);

    Optional<PlantDTO> updateById(Long plantId, PlantDTO plantDTO);

    Boolean delete(Long plantId);
}
