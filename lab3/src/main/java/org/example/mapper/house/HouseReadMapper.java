package org.example.mapper.house;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.database.entity.House;
import org.example.dto.house.HouseReadDto;
import org.example.mapper.ConverterMapper;
import org.example.mapper.street.StreetReadMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HouseReadMapper implements ConverterMapper<House, HouseReadDto> {

    private final StreetReadMapper streetReadMapper;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(House.class, HouseReadDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(HouseReadDto.class, House.class)
                .setPostConverter(toEntityConverter());
    }

    public House toEntity(HouseReadDto houseReadDto) {
        return modelMapper.map(houseReadDto, House.class);
    }

    public HouseReadDto toDto(House house) {
        return modelMapper.map(house, HouseReadDto.class);
    }

    @Override
    public void mapSpecificFields(House entity, HouseReadDto dto) {
        dto.setStreet(streetReadMapper.toDto(entity.getStreetId()));
    }

    @Override
    public void mapSpecificFields(HouseReadDto dto, House entity) {
        entity.setStreetId(streetReadMapper.toEntity(dto.getStreet()));
    }
}
