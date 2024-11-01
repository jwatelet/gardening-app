package com.johanwatelet.gardeningapp.mappers;

import com.johanwatelet.gardeningapp.entities.Plant;
import com.johanwatelet.gardeningapp.model.PlantDTO;
import org.mapstruct.Mapper;

@Mapper
public interface PlantMapper {

    PlantDTO entityToDto(Plant plant);

    Plant dtoToEntity(PlantDTO plantDTO);
}
