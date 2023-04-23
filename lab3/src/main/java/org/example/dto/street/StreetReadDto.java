package org.example.dto.street;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dto.BaseDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StreetReadDto implements BaseDto {
    private Long id;
    private String name;
    private Integer postId;
}
