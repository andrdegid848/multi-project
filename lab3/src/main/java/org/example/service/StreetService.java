package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.StreetRepository;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetCreateUpdateDto;
import org.example.dto.street.StreetReadDto;
import org.example.mapper.flat.FlatReadMapper;
import org.example.mapper.house.HouseReadMapper;
import org.example.mapper.street.StreetCreateUpdateMapper;
import org.example.mapper.street.StreetReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StreetService {

    private final StreetRepository streetRepository;
    private final StreetReadMapper streetReadMapper;
    private final HouseReadMapper houseReadMapper;
    private final FlatReadMapper flatReadMapper;
    private final StreetCreateUpdateMapper streetCreateUpdateMapper;

    public List<StreetReadDto> findAll() {
        return streetRepository.findAll().stream()
                .map(streetReadMapper::toDto)
                .toList();
    }

    public List<HouseReadDto> getAllHousesByStreetId(Long id) {
        return streetRepository.getAllHousesByStreetId(id).stream()
                .map(houseReadMapper::toDto)
                .toList();
    }

    public List<FlatReadDto> getAllFlatsByStreetId(Long id) {
        return streetRepository.getAllFlatsByStreetId(id).stream()
                .map(flatReadMapper::toDto)
                .toList();
    }

    public Optional<StreetReadDto> findById(Long id) {
        return streetRepository.findById(id)
                .map(streetReadMapper::toDto);
    }

    public StreetReadDto create(StreetCreateUpdateDto streetCreateUpdateDto) {
        return Optional.of(streetCreateUpdateDto)
                .map(streetCreateUpdateMapper::toEntity)
                .map(streetRepository::save)
                .map(streetReadMapper::toDto)
                .orElseThrow();
    }

    public Optional<StreetReadDto> update(Long id, StreetCreateUpdateDto streetCreateUpdateDto) {
        return streetRepository.findById(id)
                .map(street -> streetCreateUpdateMapper.updateStreet(streetCreateUpdateDto, street))
                .map(streetRepository::saveAndFlush)
                .map(streetReadMapper::toDto);
    }

    public boolean delete(Long id) {
        return streetRepository.findById(id)
                .map(street -> {
                    streetRepository.delete(street);
                    streetRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
