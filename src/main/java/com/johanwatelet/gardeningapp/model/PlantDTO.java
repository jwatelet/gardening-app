package com.johanwatelet.gardeningapp.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class PlantDTO {

    private Long id;

    private Integer version;

    @NotBlank(message = "Name is Mandatory")
    private String name;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
