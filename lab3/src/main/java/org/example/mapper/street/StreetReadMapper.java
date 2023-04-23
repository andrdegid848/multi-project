package org.example.mapper.street;

import lombok.RequiredArgsConstructor;
import org.example.database.entity.Street;
import org.example.dto.street.StreetReadDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreetReadMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Street toEntity(StreetReadDto streetReadDto) {
        return modelMapper.map(streetReadDto, Street.class);
    }

    public StreetReadDto toDto(Street street) {
        return modelMapper.map(street, StreetReadDto.class);
    }
}
