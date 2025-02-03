package com.helmes.homework_backend.service;

import com.helmes.homework_backend.dto.SectorDTO;
import com.helmes.homework_backend.repository.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;

    public List<SectorDTO> getAllSectorsDTO() {
        return sectorRepository.findAll().stream().map(sector -> {
            SectorDTO dto = new SectorDTO();
            dto.setId(sector.getId());
            dto.setName(sector.getName());
            dto.setParentId(sector.getParent() != null ? sector.getParent().getId() : null);
            return dto;
        }).collect(Collectors.toList());
    }
}