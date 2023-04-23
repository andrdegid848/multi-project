package org.example.mapper.flat;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.database.entity.Flat;
import org.example.dto.flat.FlatReadDto;
import org.example.mapper.ConverterMapper;
import org.example.mapper.house.HouseReadMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlatReadMapper implements ConverterMapper<Flat, FlatReadDto> {

    private final HouseReadMapper houseReadMapper;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Flat.class, FlatReadDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(FlatReadDto.class, Flat.class)
                .setPostConverter(toEntityConverter());
    }

    public Flat toEntity(FlatReadDto flatReadDto) {
        return modelMapper.map(FlatReadDto.class, Flat.class);
    }

    public FlatReadDto toDto(Flat flat) {
        return modelMapper.map(flat, FlatReadDto.class);
    }

    @Override
    public void mapSpecificFields(Flat entity, FlatReadDto dto) {
        dto.setHouse(houseReadMapper.toDto(entity.getHouseId()));
    }

    @Override
    public void mapSpecificFields(FlatReadDto dto, Flat entity) {
        entity.setHouseId(houseReadMapper.toEntity(dto.getHouse()));
    }
}
