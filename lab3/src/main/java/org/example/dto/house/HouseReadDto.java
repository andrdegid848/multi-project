package org.example.dto.house;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.database.entity.BuildingType;
import org.example.database.entity.Material;
import org.example.dto.BaseDto;
import org.example.dto.street.StreetReadDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseReadDto implements BaseDto {
    private Long id;
    private String name;
    private LocalDate buildDate;
    private Integer floorsNumber;
    private BuildingType buildingType;
    private Material material;
    private StreetReadDto street;
}
