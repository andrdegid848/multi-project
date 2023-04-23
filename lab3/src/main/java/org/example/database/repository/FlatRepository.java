package org.example.database.repository;

import org.example.database.entity.Flat;
import org.example.database.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlatRepository extends JpaRepository<Flat, Long> {

    @Query("""
            select h from House h
            where h.name like :name
            """)
    List<House> getAllByName(String name);
}
