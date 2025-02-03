package com.helmes.homework_backend.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SectorDTO {
    private Integer id;
    private String name;
    private Integer parentId;
}
