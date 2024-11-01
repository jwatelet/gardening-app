package com.johanwatelet.gardeningapp.bootstrap;

import com.johanwatelet.gardeningapp.entities.Plant;
import com.johanwatelet.gardeningapp.repositories.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final PlantRepository plantRepository;

    @Override
    public void run(String... args) throws Exception {

        if (plantRepository.count() == 0) {
            createPlants();
        }
    }

    private void createPlants() {
        Plant basil = Plant.builder()
                .name("Basil")
                .description("It is a herbaceous therophyte plant cultivated as an aromatic and condiment plant.")
                .build();

        Plant monstera = Plant.builder()
                .name("Monstera")
                .description("These are evergreen lianas, which can climb trees up to 20 m high.")
                .build();

        Plant mint = Plant.builder()
                .name("Mint")
                .description("If mints have been known and appreciated for their aromatic qualities since Antiquity, some have acquired great economic value in recent decades.")
                .build();

        plantRepository.save(basil);
        plantRepository.save(monstera);
        plantRepository.save(mint);

        log.info("Plants have been saved");
    }
}
