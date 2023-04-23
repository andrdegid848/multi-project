package org.example.database.dao.jdbc;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.database.dao.Dao;
import org.example.database.entity.Street;
import org.example.exception.StreetException;
import org.example.util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreetDaoJdbc implements Dao<Street> {

    private static final String SAVE = """
            INSERT INTO street (name, post_id)
            VALUES (?,?);
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM street
            WHERE id = ?;
            """;

    private static final String DELETE_ALL = """
            DELETE FROM street
            """;

    private static final String UPDATE = """
            UPDATE street
            SET name = ?, post_id = ?
            WHERE id = ?
            """;

    private static final String GET_BY_ID = """
            SELECT *
            FROM street
            WHERE id = ?
            """;

    private static final String FIND_ALL = """
            SELECT *
            FROM street
            """;

    private static final StreetDaoJdbc INSTANCE = new StreetDaoJdbc();

    @Override
    @SneakyThrows
    public Street save(Street entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getPostId());

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
    public void deleteByEntity(Street entity) {
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
    public Street update(Long id, Street entity) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setObject(1, entity.getName());
            preparedStatement.setObject(2, entity.getPostId());
            preparedStatement.setObject(3, id);

            preparedStatement.executeUpdate();
        }
        return entity;
    }

    @Override
    @SneakyThrows
    public Street getById(Long id) {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setObject(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next())
                throw new StreetException("This street doesn't exist");

            return buildStreet(resultSet);
        }
    }

    @Override
    @SneakyThrows
    public List<Street> getAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Street> streets = new ArrayList<>();

            while (resultSet.next()) {
                streets.add(buildStreet(resultSet));
            }

            return streets;
        }
    }

    public static StreetDaoJdbc getInstance() {
        return INSTANCE;
    }

    private Street buildStreet(ResultSet resultSet) throws SQLException {
        return Street.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .postId(resultSet.getInt("post_id"))
                .build();
    }
}
