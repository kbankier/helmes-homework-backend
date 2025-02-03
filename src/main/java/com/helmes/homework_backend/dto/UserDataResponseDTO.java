package com.helmes.homework_backend.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDataResponseDTO {
    private Long id;
    private String name;
    private Boolean agree;
    private Set<SectorDTO> sectors;
}
