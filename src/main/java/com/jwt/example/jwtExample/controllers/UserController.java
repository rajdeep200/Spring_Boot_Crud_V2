package com.jwt.example.jwtExample.controllers;

import com.jwt.example.jwtExample.Entity.User;
import com.jwt.example.jwtExample.Repository.UserRepository;
import com.jwt.example.jwtExample.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserByID(@PathVariable Long id) {
        return userService.getUserByID(id);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserByID(id);
    }
}
