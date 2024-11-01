package com.johanwatelet.gardeningapp.model;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Builder
@Data
public class PlantDTO {

    private Long id;

    private Integer version;

    private String name;

    private String description;

    private Timestamp createdAt;

    private Timestamp updatedAt;
}
