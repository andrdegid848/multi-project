package org.example.mapper.house;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.database.entity.House;
import org.example.database.repository.StreetRepository;
import org.example.dto.house.HouseCreateUpdateDto;
import org.example.mapper.ConverterMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class HouseCreateUpdateMapper implements ConverterMapper<House, HouseCreateUpdateDto> {

    private final StreetRepository streetRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    public void setupMapper() {
        modelMapper.createTypeMap(House.class, HouseCreateUpdateDto.class)
                .setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(HouseCreateUpdateDto.class, House.class)
                .setPostConverter(toEntityConverter());
    }

    public House toEntity(HouseCreateUpdateDto houseCreateUpdateDto) {
        return modelMapper.map(houseCreateUpdateDto, House.class);
    }

    public House updateHouse(HouseCreateUpdateDto houseCreateUpdateDto, House house) {
        modelMapper.typeMap(HouseCreateUpdateDto.class, House.class)
                .addMappings(mapper -> mapper.skip(House::setId));
        modelMapper.map(houseCreateUpdateDto, house);
        return house;
    }

    public HouseCreateUpdateDto toDto(House house) {
        return modelMapper.map(house, HouseCreateUpdateDto.class);
    }

    @Override
    public void mapSpecificFields(House entity, HouseCreateUpdateDto dto) {
        dto.setStreetId(entity.getStreetId().getId());
    }

    @Override
    public void mapSpecificFields(HouseCreateUpdateDto dto, House entity) {
        entity.setStreetId(Optional.ofNullable(dto.getStreetId())
                .flatMap(streetRepository::findById)
                .orElse(null));
    }
}
