package com.helmes.homework_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Set;

@Data
public class UserDataRequestDTO {

    @NotBlank(message = "Name is required!")
    private String name;

    @NotNull(message = "Agreeing the terms is required!")
    private Boolean agree;

    @NotEmpty(message = "At least one sector must be selected!")
    private Set<Integer> sectorIds;
}
