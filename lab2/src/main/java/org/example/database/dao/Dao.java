package org.example.database.dao;

import java.util.List;

public interface Dao<T> {
    T save(T entity);

    void deleteById(Long id);

    void deleteByEntity(T entity);

    void deleteAll();

    T update(Long id, T entity);

    T getById(Long id);

    List<T> getAll();
}
