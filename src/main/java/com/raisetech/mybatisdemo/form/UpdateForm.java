package com.raisetech.mybatisdemo.form;

import com.raisetech.mybatisdemo.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateForm {
    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private int age;

    public User convertToUser(int id) {
        User updateUser = new User(name,address,age);
        updateUser.setId(id);
        return updateUser;
    }


}
