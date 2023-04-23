package org.example.dto.flat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlatCreateUpdateDto implements BaseDto {

    @Positive
    @NotNull
    Integer number;

    @Positive
    @NotNull
    Integer square;

    @Positive
    @NotNull
    Integer roomNumber;

    @NotNull
    Long houseId;
}
