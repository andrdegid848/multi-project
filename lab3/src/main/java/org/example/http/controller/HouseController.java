package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseCreateUpdateDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetReadDto;
import org.example.service.HouseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseReadDto> findAll() {
        return houseService.findAll();
    }

    @GetMapping(value = "/streets/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StreetReadDto> getAllByPostId(@PathVariable("postId") Integer postId) {
        return houseService.getAllByPostId(postId);
    }

    @GetMapping(value = "/flats/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlatReadDto> getAllFlatsByHouseId(@PathVariable("id") Long id) {
        return houseService.getAllFlatsByHouseId(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public HouseReadDto findById(@PathVariable("id") Long id) {
        return houseService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public HouseReadDto create(@Validated @RequestBody HouseCreateUpdateDto houseCreateUpdateDto) {
        return houseService.create(houseCreateUpdateDto);
    }

    @PutMapping(value = "/{id}")
    public HouseReadDto update(@PathVariable("id") Long id,
                               @Validated @RequestBody HouseCreateUpdateDto houseCreateUpdateDto) {
        return houseService.update(id, houseCreateUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!houseService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
