package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.HouseRepository;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseCreateUpdateDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetReadDto;
import org.example.mapper.flat.FlatReadMapper;
import org.example.mapper.house.HouseCreateUpdateMapper;
import org.example.mapper.house.HouseReadMapper;
import org.example.mapper.street.StreetReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseReadMapper houseReadMapper;
    private final StreetReadMapper streetReadMapper;
    private final FlatReadMapper flatReadMapper;
    private final HouseCreateUpdateMapper houseCreateUpdateMapper;

    public List<HouseReadDto> findAll() {
        return houseRepository.findAll().stream()
                .map(houseReadMapper::toDto)
                .toList();
    }

    public List<FlatReadDto> getAllFlatsByHouseId(Long id) {
        return houseRepository.getAllFlatsByHouseId(id).stream()
                .map(flatReadMapper::toDto)
                .toList();
    }

    public List<StreetReadDto> getAllByPostId(Integer postId) {
        return houseRepository.getAllByPostId(postId).stream()
                .map(streetReadMapper::toDto)
                .toList();
    }

    public Optional<HouseReadDto> findById(Long id) {
        return houseRepository.findById(id)
                .map(houseReadMapper::toDto);
    }

    public HouseReadDto create(HouseCreateUpdateDto houseCreateUpdateDto) {
        return Optional.of(houseCreateUpdateDto)
                .map(houseCreateUpdateMapper::toEntity)
                .map(houseRepository::save)
                .map(houseReadMapper::toDto)
                .orElseThrow();
    }

    public Optional<HouseReadDto> update(Long id, HouseCreateUpdateDto houseCreateUpdateDto) {
        return houseRepository.findById(id)
                .map(house -> houseCreateUpdateMapper.updateHouse(houseCreateUpdateDto, house))
                .map(houseRepository::saveAndFlush)
                .map(houseReadMapper::toDto);
    }

    public boolean delete(Long id) {
        return houseRepository.findById(id)
                .map(house -> {
                    houseRepository.delete(house);
                    houseRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
