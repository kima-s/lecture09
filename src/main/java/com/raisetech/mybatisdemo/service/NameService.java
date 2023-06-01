package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.Name;

import java.util.List;

public interface NameService {
    List<Name> findAll();
}
