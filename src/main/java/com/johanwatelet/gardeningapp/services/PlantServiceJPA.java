package com.johanwatelet.gardeningapp.services;

import com.johanwatelet.gardeningapp.mappers.PlantMapper;
import com.johanwatelet.gardeningapp.model.PlantDTO;
import com.johanwatelet.gardeningapp.repositories.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlantServiceJPA implements PlantService {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    private final PlantRepository plantRepository;
    private final PlantMapper plantMapper;

    @Override
    public Page<PlantDTO> listPlants(Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);

        return plantRepository.findAll(pageRequest)
                .map(plantMapper::entityToDto);
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("name"));

        return PageRequest.of(queryPageNumber, queryPageSize, sort);
    }
}