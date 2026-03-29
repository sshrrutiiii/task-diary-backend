package com.shruti.taskmanager.controller;

import com.shruti.taskmanager.entity.User;
import com.shruti.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Email already exists");
        }
    }

    // Login user
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        Optional<User> existingUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (existingUser.isPresent()) {
            return "Login successful!";
        } else {
            return "Invalid email or password!";
        }
    }

    // Optional: Get all users
    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}