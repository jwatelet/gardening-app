package com.johanwatelet.gardeningapp.controllers;


import com.johanwatelet.gardeningapp.model.PlantDTO;
import com.johanwatelet.gardeningapp.services.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantController {

    public static final String PLANT_PATH = "/api/v1/plants";
    public static final String PLANT_PATH_ID = PLANT_PATH + "/{plantId}";

    private final PlantService plantService;

    @GetMapping(PLANT_PATH_ID)
    public PlantDTO getPlantById(@PathVariable Long plantId) {

        return plantService.getPlantById(plantId).orElseThrow(NotFoundException::new);
    }

    @GetMapping(PLANT_PATH)
    public Page<PlantDTO> listPlants(@RequestParam(required = false) Integer pageNumber,
                                     @RequestParam(required = false) Integer pageSize) {

        return plantService.listPlants(pageNumber, pageSize);
    }
}
