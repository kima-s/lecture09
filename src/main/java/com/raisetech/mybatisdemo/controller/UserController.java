package com.raisetech.mybatisdemo.controller;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.form.CreateForm;
import com.raisetech.mybatisdemo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/names")
    public List<NameResponse> names() {
        List<User> users = userService.findAll();
        List<NameResponse> nameResponses = users.stream().map(NameResponse::new).toList();
        return nameResponses;
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody @Validated CreateForm form,UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(form);
        URI url = uriBuilder
                .path("/users/" + user.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "user successfully created!"));
    }

}