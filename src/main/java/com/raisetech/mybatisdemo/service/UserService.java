package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.form.CreateForm;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(int id);

    User createUser(String name, String address, Integer age);

    void updateUser(User updateUser);

    void deleteUser(int id);
}
