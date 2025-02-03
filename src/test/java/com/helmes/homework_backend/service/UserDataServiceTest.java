package com.helmes.homework_backend.service;

import com.helmes.homework_backend.dto.UserDataRequestDTO;
import com.helmes.homework_backend.dto.UserDataResponseDTO;
import com.helmes.homework_backend.entity.Sector;
import com.helmes.homework_backend.entity.UserData;
import com.helmes.homework_backend.repository.SectorRepository;
import com.helmes.homework_backend.repository.UserDataRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDataServiceTest {

    @Mock
    private UserDataRepository userDataRepository;

    @Mock
    private SectorRepository sectorRepository;

    @InjectMocks
    private UserDataService userDataService;

    private UserDataRequestDTO requestDTO;
    private UserData userData;
    private Sector sector;

    @BeforeEach
    void setUp() {
        sector = new Sector();
        sector.setId(1);
        sector.setName("Some sector");
        sector.setParent(null);

        requestDTO = new UserDataRequestDTO();
        requestDTO.setName("Some Name");
        requestDTO.setAgree(true);
        requestDTO.setSectorIds(Set.of(1));

        userData = new UserData();
        userData.setId(100L);
        userData.setName("Some Name");
        userData.setAgree(true);
        userData.setSectors(Set.of(sector));
    }

    @Test
    void createUserData_validRequest_returnsUserDataResponse() {
        when(sectorRepository.findById(1)).thenReturn(Optional.of(sector));
        when(userDataRepository.save(any(UserData.class))).thenReturn(userData);

        UserDataResponseDTO responseDTO = userDataService.createUserData(requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(100L);
        assertThat(responseDTO.getName()).isEqualTo("Some Name");
        assertThat(responseDTO.getAgree()).isTrue();
        assertThat(responseDTO.getSectors()).hasSize(1);

        verify(sectorRepository, times(1)).findById(1);
        verify(userDataRepository, times(1)).save(any(UserData.class));
    }

    @Test
    void updateUserData_existingUser_returnsUpdatedUserDataResponse() {
        when(userDataRepository.findById(100L)).thenReturn(Optional.of(userData));
        when(sectorRepository.findById(1)).thenReturn(Optional.of(sector));
        when(userDataRepository.save(any(UserData.class))).thenReturn(userData);

        UserDataResponseDTO responseDTO = userDataService.updateUserData(100L, requestDTO);

        assertThat(responseDTO).isNotNull();
        assertThat(responseDTO.getId()).isEqualTo(100L);
        assertThat(responseDTO.getName()).isEqualTo("Some Name");
        assertThat(responseDTO.getAgree()).isTrue();
        assertThat(responseDTO.getSectors()).hasSize(1);

        verify(userDataRepository, times(1)).findById(100L);
        verify(userDataRepository, times(1)).save(any(UserData.class));
    }

    @Test
    void updateUserData_nonExistingUser_throwsEntityNotFoundException() {
        when(userDataRepository.findById(100L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userDataService.updateUserData(100L, requestDTO))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("User data not found for ID: 100");

        verify(userDataRepository, times(1)).findById(100L);
        verify(userDataRepository, never()).save(any(UserData.class));
    }
}

