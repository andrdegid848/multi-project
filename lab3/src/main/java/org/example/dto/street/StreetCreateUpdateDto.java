package org.example.dto.street;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetCreateUpdateDto implements BaseDto {

    @NotBlank
    private String name;

    @Positive
    @NotNull
    private Integer postId;
}
