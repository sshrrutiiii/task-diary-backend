package com.shruti.taskmanager.controller;

import com.shruti.taskmanager.entity.User;
import com.shruti.taskmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "https://your-frontend.vercel.app")// Allow frontend React
public class UserController {

    @Autowired
    private UserService userService;

    // Register new user
    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        return userService.registerUser(user);
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