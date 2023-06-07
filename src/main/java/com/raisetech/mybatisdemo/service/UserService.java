package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.form.CreateForm;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User createUser(CreateForm form);
}
