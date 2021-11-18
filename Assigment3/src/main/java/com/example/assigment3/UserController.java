package com.example.assigment3;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    private final UserHandler handler;

    UserController(UserHandler handler) {
        this.handler = handler;
    }

    @GetMapping("/users")
    List<User> all() {
        return handler.all();
    }

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return handler.newUser(newUser);
    }

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return handler.one(id);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
        return handler.replaceUser(newUser, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        handler.deleteUser(id);
    }
}