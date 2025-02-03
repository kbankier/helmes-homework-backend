package com.helmes.homework_backend.controller;

import com.helmes.homework_backend.dto.SectorDTO;
import com.helmes.homework_backend.dto.UserDataRequestDTO;
import com.helmes.homework_backend.dto.UserDataResponseDTO;
import com.helmes.homework_backend.service.SectorService;
import com.helmes.homework_backend.service.UserDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final SectorService sectorService;
    private final UserDataService userDataService;

    @GetMapping("/sectors")
    public ResponseEntity<List<SectorDTO>> getSectors() {
        List<SectorDTO> sectors = sectorService.getAllSectorsDTO();
        return ResponseEntity.ok(sectors);
    }

    @PostMapping("/user-data")
    public ResponseEntity<UserDataResponseDTO> createUserData(@Valid @RequestBody UserDataRequestDTO request) {
        UserDataResponseDTO response = userDataService.createUserData(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/user-data/{id}")
    public ResponseEntity<UserDataResponseDTO> updateUserData(@PathVariable Long id,
                                                              @Valid @RequestBody UserDataRequestDTO request) {
        UserDataResponseDTO response = userDataService.updateUserData(id, request);
        return ResponseEntity.ok(response);
    }
}
