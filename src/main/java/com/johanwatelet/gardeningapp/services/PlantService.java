package com.johanwatelet.gardeningapp.services;

import com.johanwatelet.gardeningapp.model.PlantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlantService {

    Page<PlantDTO> listPlants(Integer pageNumber, Integer pageSize);
}
