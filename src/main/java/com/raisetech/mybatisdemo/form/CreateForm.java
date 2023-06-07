package com.raisetech.mybatisdemo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateForm {
    @NotNull
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotNull
    private int age;


}