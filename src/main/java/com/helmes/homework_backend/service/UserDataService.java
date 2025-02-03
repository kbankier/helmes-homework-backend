package com.helmes.homework_backend.service;

import com.helmes.homework_backend.dto.SectorDTO;
import com.helmes.homework_backend.dto.UserDataRequestDTO;
import com.helmes.homework_backend.dto.UserDataResponseDTO;
import com.helmes.homework_backend.entity.Sector;
import com.helmes.homework_backend.entity.UserData;
import com.helmes.homework_backend.repository.SectorRepository;
import com.helmes.homework_backend.repository.UserDataRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final UserDataRepository userDataRepository;
    private final SectorRepository sectorRepository;

    @Transactional
    public UserDataResponseDTO createUserData(UserDataRequestDTO request) {
        UserData userData = mapToUserData(request, new UserData());
        return convertToResponseDTO(userDataRepository.save(userData));
    }

    @Transactional
    public UserDataResponseDTO updateUserData(Long id, UserDataRequestDTO request) {
        UserData userData = userDataRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User data not found for ID: %s", id)));
        return convertToResponseDTO(userDataRepository.save(mapToUserData(request, userData)));
    }

    private UserData mapToUserData(UserDataRequestDTO request, UserData userData) {
        Set<Sector> sectors = fetchSectorsByIds(request.getSectorIds());
        userData.setName(request.getName());
        userData.setAgree(request.getAgree());
        userData.setSectors(sectors);
        return userData;
    }

    private Set<Sector> fetchSectorsByIds(Set<Integer> sectorIds) {
        Set<Sector> sectors = sectorIds.stream()
                .map(sectorRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        if (sectors.size() < sectorIds.size()) {
            throw new IllegalArgumentException("One or more sectors not found for the provided IDs!");
        }
        return sectors;
    }

    private UserDataResponseDTO convertToResponseDTO(UserData userData) {
        return new UserDataResponseDTO()
                .setId(userData.getId())
                .setName(userData.getName())
                .setAgree(userData.getAgree())
                .setSectors(userData.getSectors().stream()
                        .map(sector -> new SectorDTO()
                                .setId(sector.getId())
                                .setName(sector.getName())
                                .setParentId(sector.getParent() != null ? sector.getParent().getId() : null))
                        .collect(Collectors.toSet()));
    }
}