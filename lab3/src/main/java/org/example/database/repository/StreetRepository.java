package org.example.database.repository;

import org.example.database.entity.Flat;
import org.example.database.entity.House;
import org.example.database.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

    @Query("""
            select h from House h
            where h.streetId.id = :id
            """)
    List<House> getAllHousesByStreetId(Long id);


    @Query("""
            select f from Flat f
            join f.houseId h
            join h.streetId s
            where s.id = :id
            """)
    List<Flat> getAllFlatsByStreetId(Long id);
}
