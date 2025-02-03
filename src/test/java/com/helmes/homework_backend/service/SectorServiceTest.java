package com.helmes.homework_backend.service;

import com.helmes.homework_backend.dto.SectorDTO;
import com.helmes.homework_backend.entity.Sector;
import com.helmes.homework_backend.repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SectorServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private SectorService sectorService;

    private Sector parentSector;
    private Sector childSector;

    @BeforeEach
    void setUp() {
        parentSector = new Sector();
        parentSector.setId(1);
        parentSector.setName("Parent sector");
        parentSector.setParent(null);

        childSector = new Sector();
        childSector.setId(2);
        childSector.setName("Child sector");
        childSector.setParent(parentSector);
    }

    @Test
    void getAllSectorsDTO_returnsSectorDTOList() {
        when(sectorRepository.findAll()).thenReturn(Arrays.asList(parentSector, childSector));

        List<SectorDTO> result = sectorService.getAllSectorsDTO();

        assertThat(result).isNotNull().hasSize(2);

        assertThat(result.getFirst().getId()).isEqualTo(parentSector.getId());
        assertThat(result.getFirst().getName()).isEqualTo(parentSector.getName());
        assertThat(result.getFirst().getParentId()).isNull();

        assertThat(result.get(1).getId()).isEqualTo(childSector.getId());
        assertThat(result.get(1).getName()).isEqualTo(childSector.getName());
        assertThat(result.get(1).getParentId()).isEqualTo(parentSector.getId());

        verify(sectorRepository, atLeastOnce()).findAll();
    }
}

