package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.ThirdLab;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetReadDto;
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
class StreetServiceTest {

    private final StreetService streetService;
    private final Long STREET_ID = 1L;

    @Test
    void findAll() {
        List<StreetReadDto> streetResults = streetService.findAll();
        assertThat(streetResults).hasSize(3);
    }

    @Test
    void getAllHousesByStreetId() {
        List<HouseReadDto> houseResults = streetService.getAllHousesByStreetId(STREET_ID);
        assertThat(houseResults).hasSize(2);
    }

    @Test
    void getAllFlatsByStreetId() {
        List<FlatReadDto> flatResults = streetService.getAllFlatsByStreetId(STREET_ID);
        assertThat(flatResults).hasSize(3);
    }

    @Test
    void findById() {
        Optional<StreetReadDto> result = streetService.findById(STREET_ID);

        assertTrue(result.isPresent());

        result.ifPresent(it -> {
            assertEquals(STREET_ID, it.getId());
            assertEquals("street1", it.getName());
            assertEquals(1, it.getPostId());
        });
    }
}