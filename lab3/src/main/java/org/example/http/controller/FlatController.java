package org.example.http.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.flat.FlatCreateUpdateDto;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.service.FlatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/flats")
@RequiredArgsConstructor
public class FlatController {

    private final FlatService flatService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<FlatReadDto> findAll() {
        return flatService.findAll();
    }

    @GetMapping(value = "/houses/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<HouseReadDto> getAllByName(@PathVariable("name") String name) {
        return flatService.getAllByName(name);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public FlatReadDto findById(@PathVariable("id") Long id) {
        return flatService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public FlatReadDto create(@Validated @RequestBody FlatCreateUpdateDto flatCreateUpdateDto) {
        return flatService.create(flatCreateUpdateDto);
    }

    @PutMapping(value = "/{id}")
    public FlatReadDto update(@PathVariable("id") Long id,
                              @Validated @RequestBody FlatCreateUpdateDto flatCreateUpdateDto) {
        return flatService.update(id, flatCreateUpdateDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        if (!flatService.delete(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
