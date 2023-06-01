package com.raisetech.mybatisdemo.repository;

import com.raisetech.mybatisdemo.entity.Name;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NameMapper {
    @Select("SELECT * FROM names")
    List<Name> findAll();

}
