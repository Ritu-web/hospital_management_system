package com.hospital.hospitalmanagement.usercontroller;
import com.hospital.hospitalmanagement.userentity.User;
import com.hospital.hospitalmanagement.userservice.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    // Constructor injection
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ POST API to create user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("CONTROLLER --> User received: {}", user);
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(201).body(savedUser); // 201 Created
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    // ✅ GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // 200 OK + JSON list of users
    }
}

