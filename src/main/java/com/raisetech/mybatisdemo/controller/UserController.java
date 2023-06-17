package com.raisetech.mybatisdemo.controller;

import com.raisetech.mybatisdemo.entity.User;
import com.raisetech.mybatisdemo.exception.ResourceNotFoundException;
import com.raisetech.mybatisdemo.form.CreateForm;
import com.raisetech.mybatisdemo.form.UpdateForm;
import com.raisetech.mybatisdemo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
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

    @GetMapping("/names/{id}")
    public User getUser(@PathVariable("id") int id){
        return userService.findById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<Map<String, String>> create(
            @RequestBody @Validated CreateForm form,UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(form.getName(),form.getAddress(), form.getAge());
        URI url = uriBuilder
                .path("/users/" + user.getId())
                .build()
                .toUri();
        return ResponseEntity.created(url).body(Map.of("message", "user successfully created!"));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> update(
            @PathVariable("id") int id, @RequestBody UpdateForm form) {
        userService.updateUser(form.convertToUser(id));
        return ResponseEntity.ok(Map.of("message", "user successfully updated"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable("id") int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(Map.of("message", "user successfully deleted"));
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            ResourceNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }


}
