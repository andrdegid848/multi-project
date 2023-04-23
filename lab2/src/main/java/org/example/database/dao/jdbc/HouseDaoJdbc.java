package org.example.database.dao.jdbc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.database.dao.Dao;
import org.example.database.entity.BuildingType;
import org.example.database.entity.House;
import org.example.database.entity.Street;
import org.example.exception.HouseException;
import org.example.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HouseDaoJdbc implements Dao<House> {

    private static final String SAVE = """
            INSERT INTO house (name, build_date, floors_number, building_type, street_id)
            VALUES (?,?,?,?,?);
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM house
            WHERE id = ?;
            """;

    private static final String DELETE_ALL = """
            DELETE FROM house
            """;

    private static final String UPDATE = """
            UPDATE house
            SET name = ?, build_date = ?, floors_number = ?, building_type = ?, street_id = ?
            WHERE id = ?
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM house
            WHERE id = ?
            """;

    private static final String GET_STREET_BY_ID = """
            SELECT *
            FROM street
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM house
            """;

    private static final HouseDaoJdbc INSTANCE = new HouseDaoJdbc();

    @Override
    @SneakyThrows
    public House save(House entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getBuildDate());
            preparedStatement.setObject(3, entity.getFloorsNumber());
            preparedStatement.setObject(4, entity.getBuildingType());
            preparedStatement.setObject(5, entity.getStreetId());

            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    @SneakyThrows
    public void deleteById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BY_ID)) {
            preparedStatement.setObject(1, id);

            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteByEntity(House entity) {
        deleteById(entity.getId());
    }

    @Override
    @SneakyThrows
    public void deleteAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public House update(Long id, House entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getBuildDate());
            preparedStatement.setObject(3, entity.getFloorsNumber());
            preparedStatement.setObject(4, entity.getBuildingType());
            preparedStatement.setObject(5, entity.getStreetId());
            preparedStatement.setObject(6, id);

            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    @SneakyThrows
    public House getById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new HouseException("This house doesn't exist");

            return buildHouse(resultSet);
        }
    }

    @Override
    @SneakyThrows
    public List<House> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<House> houses = new ArrayList<>();

            while (resultSet.next()) {
                houses.add(buildHouse(resultSet));
            }

            return houses;
        }
    }

    public static HouseDaoJdbc getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    private Street getStreetById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_STREET_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new HouseException("This street doesn't exist");

            return Street.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .postId(resultSet.getInt("post_id"))
                    .build();
        }
    }

    private House buildHouse(ResultSet resultSet) throws SQLException {
        return House.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .buildDate(resultSet.getObject("build_date", Date.class).toLocalDate())
                .floorsNumber(resultSet.getInt("floors_number"))
                .buildingType(BuildingType.valueOf(resultSet.getString("building_type")))
                .streetId(getStreetById(resultSet.getLong("street_id")))
                .build();
    }
}
