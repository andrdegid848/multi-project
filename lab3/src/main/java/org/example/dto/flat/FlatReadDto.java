package org.example.dto.flat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.BaseDto;
import org.example.dto.house.HouseReadDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatReadDto implements BaseDto {
    Long id;
    Integer number;
    Integer square;
    Integer roomNumber;
    HouseReadDto house;
}
