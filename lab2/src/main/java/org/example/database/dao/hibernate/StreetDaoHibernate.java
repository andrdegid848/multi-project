package org.example.database.dao.hibernate;

import lombok.RequiredArgsConstructor;
import org.example.database.dao.Dao;
import org.example.database.entity.Street;
import org.hibernate.Session;

import java.util.List;

@RequiredArgsConstructor
public class StreetDaoHibernate implements Dao<Street> {

    private final Session session;

    private static final String DELETE_ALL = """
            DELETE FROM Street
            """;

    private static final String GET_ALL = """
            SELECT s FROM Street s
            """;

    @Override
    public Street save(Street entity) {
        session.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        Street street = getById(id);
        deleteByEntity(street);
    }

    @Override
    public void deleteByEntity(Street entity) {
        session.remove(entity);
    }

    @Override
    public void deleteAll() {
        session.createQuery(DELETE_ALL, Street.class);
    }

    @Override
    public Street update(Long id, Street entity) {
        Street street = getById(id);
        street.setName(entity.getName());
        street.setPostId(entity.getPostId());
        session.flush();
        return entity;
    }

    @Override
    public Street getById(Long id) {
        return session.get(Street.class, id);
    }

    @Override
    public List<Street> getAll() {
        return session
                .createQuery(GET_ALL, Street.class)
                .list();
    }
}
