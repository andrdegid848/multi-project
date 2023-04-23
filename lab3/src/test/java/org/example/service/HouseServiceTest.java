package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.ThirdLab;
import org.example.database.entity.BuildingType;
import org.example.database.entity.Material;
import org.example.dto.flat.FlatReadDto;
import org.example.dto.house.HouseReadDto;
import org.example.dto.street.StreetReadDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
class HouseServiceTest {

    private final HouseService houseService;
    private final Long HOUSE_ID = 1L;

    @Test
    void findAll() {
        List<HouseReadDto> houseResults = houseService.findAll();
        assertThat(houseResults).hasSize(4);
    }

    @Test
    void getAllFlatsByHouseId() {
        List<FlatReadDto> flatResults = houseService.getAllFlatsByHouseId(HOUSE_ID);
        assertThat(flatResults).hasSize(2);
    }

    @Test
    void getAllByPostId() {
        Integer postId = 1;
        List<StreetReadDto> streetResults = houseService.getAllByPostId(postId);
        assertThat(streetResults).hasSize(2);
    }

    @Test
    void findById() {
        Optional<HouseReadDto> result = houseService.findById(HOUSE_ID);

        assertTrue(result.isPresent());

        result.ifPresent(it -> {
            assertEquals("house1", it.getName());
            assertEquals(LocalDate.of(2000, 1, 1), it.getBuildDate());
            assertEquals(5, it.getFloorsNumber());
            assertEquals(BuildingType.HOUSE, it.getBuildingType());
            assertEquals(1, it.getStreet().getId());
            assertEquals(Material.WOOD, it.getMaterial());
        });
    }
}