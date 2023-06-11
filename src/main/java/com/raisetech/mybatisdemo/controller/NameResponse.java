package com.raisetech.mybatisdemo.controller;

import com.raisetech.mybatisdemo.entity.User;

public class NameResponse {
    private String name;

    public NameResponse(User name) {
        this.name = name.getName();
    }

    public String getName() {
        return name;
    }
}
