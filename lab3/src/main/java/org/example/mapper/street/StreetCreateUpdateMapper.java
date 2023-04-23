package org.example.mapper.street;

import lombok.RequiredArgsConstructor;
import org.example.database.entity.Street;
import org.example.dto.street.StreetCreateUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StreetCreateUpdateMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Street toEntity(StreetCreateUpdateDto streetCreateUpdateDto) {
        return modelMapper.map(streetCreateUpdateDto, Street.class);
    }

    public Street updateStreet(StreetCreateUpdateDto streetCreateUpdateDto, Street street) {
        modelMapper.typeMap(StreetCreateUpdateDto.class, Street.class)
                .addMappings(mapper -> mapper.skip(Street::setId));
        modelMapper.map(streetCreateUpdateDto, street);
        return street;
    }

    public StreetCreateUpdateDto toDto(Street street) {
        return modelMapper.map(street, StreetCreateUpdateDto.class);
    }
}
