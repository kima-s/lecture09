package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.Name;
import com.raisetech.mybatisdemo.repository.NameMapper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NameServiceImpl implements NameService{
    private final NameMapper nameMapper;

    public NameServiceImpl(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
    }
    @Override
    public List<Name> findAll(){
        return nameMapper.findAll();
    }
}
