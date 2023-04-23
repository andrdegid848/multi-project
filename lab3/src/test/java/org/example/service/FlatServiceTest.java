package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.ThirdLab;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql({
        "classpath:sql/init.sql"
})
@SpringBootTest(classes = ThirdLab.class)
@Transactional
@RequiredArgsConstructor
class FlatServiceTest {

    private final FlatService flatService;

    @Test
    void findAll() {
        List<FlatReadDto> flatResults = flatService.findAll();
        assertThat(flatResults).hasSize(5);
    }

    @Test
    void getAllByName() {
        String houseName = "house1";
        List<HouseReadDto> houseResults = flatService.getAllByName(houseName);
        assertThat(houseResults).hasSize(1);
    }

    @Test
    void findById() {
        Long flatId = 1L;
        Optional<FlatReadDto> result = flatService.findById(flatId);

        assertTrue(result.isPresent());

        result.ifPresent(it -> {
            assertEquals(10, it.getNumber());
            assertEquals(10, it.getSquare());
            assertEquals(2, it.getRoomNumber());
            assertEquals(1, it.getHouse().getId());
        });
    }
}