package org.example.database.dao.hibernate;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.database.dao.Dao;
import org.example.database.entity.House;
import org.example.database.entity.Street;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class HouseDaoHibernate implements Dao<House> {

    private final SessionFactory sessionFactory;

    private static final String DELETE_ALL = """
            DELETE FROM House
            """;

    private static final String GET_ALL = """
            SELECT h FROM House h
            """;

    private static final String GET_STREET_BY_ID = """
            SELECT s FROM Street s
            WHERE s.id =:id
            """;

    @Override
    public House save(House entity) {
        sessionFactory.getCurrentSession().persist(entity);
        return entity;
    }

    @Override
    public void deleteById(Long id) {
        House house = getById(id);
        deleteByEntity(house);
    }

    @Override
    public void deleteByEntity(House entity) {
        sessionFactory.getCurrentSession().remove(entity);
    }

    @Override
    public void deleteAll() {
        sessionFactory.getCurrentSession()
                .createQuery(DELETE_ALL, House.class);
    }

    @Override
    public House update(Long id, House entity) {
        Session session = sessionFactory.getCurrentSession();
        House house = getById(id);
        house.setName(entity.getName());
        house.setBuildingType(entity.getBuildingType());
        house.setBuildDate(entity.getBuildDate());
        house.setFloorsNumber(entity.getFloorsNumber());
        house.setStreetId(getStreetById(entity.getId()));
        session.flush();
        return entity;
    }

    @Override
    public House getById(Long id) {
        return sessionFactory.getCurrentSession().get(House.class, id);
    }

    @Override
    public List<House> getAll() {
        return sessionFactory.getCurrentSession()
                .createQuery(GET_ALL, House.class)
                .list();
    }

    private Street getStreetById(Long id) {
        return sessionFactory.getCurrentSession()
                .createQuery(GET_STREET_BY_ID, Street.class)
                .setParameter("id", id)
                .uniqueResult();
    }
}
