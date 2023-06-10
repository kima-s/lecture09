package com.raisetech.mybatisdemo.repository;

import com.raisetech.mybatisdemo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM users")
    List<User> findAll();

    @Insert("INSERT INTO users (name,address,age) VALUES (#{name},#{address},#{age})")
    void createUser(User user);
}
