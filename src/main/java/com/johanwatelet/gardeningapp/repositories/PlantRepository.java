package com.johanwatelet.gardeningapp.repositories;

import com.johanwatelet.gardeningapp.entities.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
}
