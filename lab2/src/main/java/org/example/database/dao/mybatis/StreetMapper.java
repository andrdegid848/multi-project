package org.example.database.dao.mybatis;

import org.apache.ibatis.annotations.*;
import org.example.database.entity.Street;

import java.util.List;

@Mapper
public interface StreetMapper {

    @Insert("INSERT INTO street (name, post_id) VALUES (#{name}, #{postId})")
    void save(Street street);

    @Delete("DELETE FROM street WHERE id = #{id}")
    void deleteById(long id);

    @Delete("DELETE FROM street WHERE id = #{id}")
    void deleteByEntity(Street street);

    @Delete("DELETE FROM streets")
    void deleteAll();

    @Update("UPDATE street SET name = #{name}, post_id = #{postId} WHERE id = #{id}")
    void update(Street street);

    @Select("SELECT * FROM street WHERE id = #{id}")
    Street getById(long id);

    @Select("SELECT * FROM street")
    List<Street> getAll();
}
