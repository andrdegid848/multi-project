package org.example.database.dao.mybatis;

import org.apache.ibatis.annotations.*;
import org.example.database.entity.House;

import java.util.List;

@Mapper
public interface HouseMapper {

    @Insert("INSERT INTO house (name, build_date, floors_number, building_type, street_id) " +
            "VALUES (#{name}, #{buildDate}, #{floorsNumber}, #{buildingType}, #{streetId.getId})")
    void save(House house);

    @Delete("DELETE FROM house WHERE id = #{id}")
    void deleteById(Long id);

    @Delete("DELETE FROM house WHERE id = #{id}")
    void deleteByEntity(House house);

    @Delete("DELETE FROM house")
    void deleteAll();

    @Update("UPDATE house " +
            "SET name = #{name}, build_date = #{buildDate}, floors_number = #{floorsNumber}, building_type = #{buildingType}, street_id = #{streetId.getId} " +
            "WHERE id = #{id}")
    void update(House house);

    @Select("SELECT * FROM houses WHERE id = #{id}")
    House getById(Long id);

    @Select("SELECT * FROM houses")
    List<House> getAll();
}
