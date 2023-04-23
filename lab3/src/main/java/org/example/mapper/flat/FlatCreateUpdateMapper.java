package org.example.mapper.flat;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.database.entity.Flat;
import org.example.database.repository.HouseRepository;
import org.example.dto.flat.FlatCreateUpdateDto;
import org.example.mapper.ConverterMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FlatCreateUpdateMapper implements ConverterMapper<Flat, FlatCreateUpdateDto> {

    private final HouseRepository houseRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(Flat.class, FlatCreateUpdateDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(FlatCreateUpdateDto.class, Flat.class)
                .setPostConverter(toEntityConverter());
    }

    public Flat toEntity(FlatCreateUpdateDto flatCreateUpdateDto) {
        return modelMapper.map(flatCreateUpdateDto, Flat.class);
    }

    public Flat updateFlat(FlatCreateUpdateDto flatCreateUpdateDto, Flat flat) {
        modelMapper.typeMap(FlatCreateUpdateDto.class, Flat.class)
                .addMappings(mapper -> mapper.skip(Flat::setId));
        modelMapper.map(flatCreateUpdateDto, flat);
        return flat;
    }

    public FlatCreateUpdateDto toDto(Flat flat) {
        return modelMapper.map(flat, FlatCreateUpdateDto.class);
    }

    @Override
    public void mapSpecificFields(Flat entity, FlatCreateUpdateDto dto) {
        dto.setHouseId(entity.getHouseId().getId());
    }

    @Override
    public void mapSpecificFields(FlatCreateUpdateDto dto, Flat entity) {
        entity.setHouseId(Optional.ofNullable(dto.getHouseId())
                .flatMap(houseRepository::findById)
                .orElse(null));
    }
}
