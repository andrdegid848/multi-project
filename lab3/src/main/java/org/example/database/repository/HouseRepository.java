package org.example.database.repository;

import org.example.database.entity.Flat;
import org.example.database.entity.House;
import org.example.database.entity.Street;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {

    @Query("""
            select s from Street s
            where s.postId = :postId
            """)
    List<Street> getAllByPostId(Integer postId);

    @Query("""
            select f from Flat f
            join f.houseId h
            where h.id = :id
            """)
    List<Flat> getAllFlatsByHouseId(Long id);
}
