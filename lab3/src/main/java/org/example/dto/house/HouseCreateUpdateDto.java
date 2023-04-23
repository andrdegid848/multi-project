package org.example.dto.house;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.database.entity.BuildingType;
import org.example.database.entity.Material;
import org.example.dto.BaseDto;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseCreateUpdateDto implements BaseDto {

    @NotBlank
    @Size(min = 3, max = 15)
    String name;

    @Past
    @NotNull
    LocalDate buildDate;

    @PositiveOrZero
    @NotNull
    Integer floorsNumber;

    BuildingType buildingType;

    Material material;

    @NotNull
    Long streetId;
}
