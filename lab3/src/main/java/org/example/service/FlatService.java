package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.database.repository.FlatRepository;
import org.example.dto.flat.FlatCreateUpdateDto;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.mapper.flat.FlatCreateUpdateMapper;
import org.example.mapper.flat.FlatReadMapper;
import org.example.mapper.house.HouseReadMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FlatService {

    private final FlatRepository flatRepository;
    private final FlatReadMapper flatReadMapper;
    private final HouseReadMapper houseReadMapper;
    private final FlatCreateUpdateMapper flatCreateUpdateMapper;

    public List<FlatReadDto> findAll() {
        return flatRepository.findAll().stream()
                .map(flatReadMapper::toDto)
                .toList();
    }

    public List<HouseReadDto> getAllByName(String name) {
        return flatRepository.getAllByName(name).stream()
                .map(houseReadMapper::toDto)
                .toList();
    }

    public Optional<FlatReadDto> findById(Long id) {
        return flatRepository.findById(id)
                .map(flatReadMapper::toDto);
    }

    public FlatReadDto create(FlatCreateUpdateDto flatCreateUpdateDto) {
        return Optional.of(flatCreateUpdateDto)
                .map(flatCreateUpdateMapper::toEntity)
                .map(flatRepository::save)
                .map(flatReadMapper::toDto)
                .orElseThrow();
    }

    public Optional<FlatReadDto> update(Long id, FlatCreateUpdateDto flatCreateUpdateDto) {
        return flatRepository.findById(id)
                .map(flat -> flatCreateUpdateMapper.updateFlat(flatCreateUpdateDto, flat))
                .map(flatRepository::saveAndFlush)
                .map(flatReadMapper::toDto);
    }

    public boolean delete(Long id) {
        return flatRepository.findById(id)
                .map(flat -> {
                    flatRepository.delete(flat);
                    flatRepository.flush();
                    return true;
                })
                .orElse(false);
    }
}
