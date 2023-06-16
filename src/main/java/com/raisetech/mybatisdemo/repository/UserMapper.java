package com.raisetech.mybatisdemo.repository;

import com.raisetech.mybatisdemo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users (name,address,age) VALUES (#{name},#{address},#{age})")
    @Options(useGeneratedKeys=true, keyColumn="id",keyProperty = "id")
    void createUser(User user);

    @Update("UPDATE users SET name = #{name}, age = #{age} , address = #{address} where id = #{id}")
    void updateUser(User updateUser);

    @Delete("DELETE FROM users where id = #{id}")
    void deleteUser(int id);
}
