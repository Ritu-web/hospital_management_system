package com.hospital.hospitalmanagement.usercontroller;
import com.hospital.hospitalmanagement.userentity.User;
import com.hospital.hospitalmanagement.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.hospital.hospitalmanagement.dto.ErrorResponse;

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
    public ResponseEntity<?> createUser(@RequestBody User user) {
        logger.info("CONTROLLER --> User received: {}", user);

        if (!user.getPhoneNumber().matches("\\d{10}")) {
            ErrorResponse error = new ErrorResponse(400, "Phone number must be exactly 10 digits");
            return ResponseEntity.status(400).body(error);
        }

        // check for duplicate phone
        Optional<User> existingUser = userService.getUserByPhone(user.getPhoneNumber());
        if (existingUser.isPresent()) {
            ErrorResponse error = new ErrorResponse(400, "User with this phone number already exists");
            return ResponseEntity.status(400).body(error);
        }

        User savedUser = userService.createUser(user);
        return ResponseEntity.status(201).body(savedUser);

    }


    @GetMapping("/{id}") // request params
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    // ✅ GET all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // 200 OK + JSON list of users
    }

    @GetMapping("/phone/{phoneNumber}")
    public ResponseEntity<?> getUserByPhone(@PathVariable String phoneNumber) {
        if (!phoneNumber.matches("\\d{10}")) {
            ErrorResponse error = new ErrorResponse(400, "Phone number must be exactly 10 digits");
            return ResponseEntity.status(400).body(error);
        }
        return userService.getUserByPhone(phoneNumber).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        System.out.println("*****************Controller: LINE 81 **************************************" + id + "REQUEST BODY" + updatedUser);
        Optional<User> existingUserOpt = userService.getUserById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found with ID " + id);
        }

        User existingUser = existingUserOpt.get();

        // ✅ Update fields only if non-null
        if (updatedUser.getFirstName() != null) {
            existingUser.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            existingUser.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        if (updatedUser.getRole() != null) {
            existingUser.setRole(updatedUser.getRole());
        }

        existingUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userService.createUser(existingUser); // save updated user
        return ResponseEntity.ok(savedUser);
    }


}

