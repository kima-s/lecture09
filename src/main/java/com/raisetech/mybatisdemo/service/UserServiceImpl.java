package com.raisetech.mybatisdemo.service;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.exception.ResourceNotFoundException;
import com.raisetech.mybatisdemo.repository.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(int id) {
        Optional<User> user =this.userMapper.findById(id);
        return user.orElseThrow(()->new ResourceNotFoundException("resource not found"));
    }

    @Override
    public User createUser(String name, String address, Integer age) {
        User user = new User(name, address, age);
        userMapper.createUser(user);
        return user;
    }

    @Override
    public void updateUser(User updateUser) {
        User user = userMapper.findById(updateUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        if (Objects.nonNull(user)) {
            userMapper.updateUser(updateUser);
        }
    }

    @Override
    public void deleteUser(int id){
        User user = userMapper.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("resource not found"));
        if (Objects.nonNull(user)) {
            userMapper.deleteUser(id);
        }
    };
}
