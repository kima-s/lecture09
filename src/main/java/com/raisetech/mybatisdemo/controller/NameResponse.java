package com.raisetech.mybatisdemo.controller;

import com.raisetech.mybatisdemo.entity.Name;

public class NameResponse {
    private String name;

    public NameResponse(Name name) {
        this.name = name.getName();
    }

    public String getName() {
        return name;
    }
}
