package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetCreateUpdateDto;
import org.example.dto.street.StreetReadDto;
import org.example.service.StreetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/streets")
@RequiredArgsConstructor
public class StreetController {

    private final StreetService streetService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StreetReadDto> findAll() {
        return streetService.findAll();
    }

    @GetMapping(value = "/houses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseReadDto> getAllHouseByStreetId(@PathVariable("id") Long id) {
        return streetService.getAllHousesByStreetId(id);
    }

    @GetMapping(value = "/flats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlatReadDto> getAllFlatsByStreetId(@PathVariable("id") Long id) {
        return streetService.getAllFlatsByStreetId(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StreetReadDto findById(@PathVariable("id") Long id) {
        return streetService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public StreetReadDto create(@Validated @RequestBody StreetCreateUpdateDto streetCreateUpdateDto) {
        return streetService.create(streetCreateUpdateDto);
    }

    @PutMapping(value = "/{id}")
    public StreetReadDto update(@PathVariable("id") Long id,
                                @Validated @RequestBody StreetCreateUpdateDto streetCreateUpdateDto) {
        return streetService.update(id, streetCreateUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!streetService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
