package com.johanwatelet.gardeningapp.controllers;


import com.johanwatelet.gardeningapp.model.PlantDTO;
import com.johanwatelet.gardeningapp.services.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PlantController {

    public static final String PLANT_PATH = "/api/v1/plants";
    public static final String PLANT_PATH_ID = PLANT_PATH + "/{plantId}";

    private final PlantService plantService;

    @PostMapping(PLANT_PATH)
    public ResponseEntity<PlantDTO> createPlant(@RequestBody PlantDTO plantDTO) {

        PlantDTO savedPlant = plantService.createPlant(plantDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{plantId}")
                .buildAndExpand(savedPlant.getId()).toUri();

        return ResponseEntity.created(location).body(savedPlant);
    }

    @GetMapping(PLANT_PATH_ID)
    public ResponseEntity<PlantDTO> getPlantById(@PathVariable Long plantId) {
        PlantDTO returnValue = plantService.getPlantById(plantId).orElseThrow(NotFoundException::new);

        return ResponseEntity.ok(returnValue);
    }

    @GetMapping(PLANT_PATH)
    public ResponseEntity<Page<PlantDTO>> listPlants(@RequestParam(required = false) Integer pageNumber,
                                                     @RequestParam(required = false) Integer pageSize) {

        Page<PlantDTO> returnValues = plantService.listPlants(pageNumber, pageSize);

        return ResponseEntity.ok(returnValues);
    }
}
