package com.hospital.hospitalmanagement.userservice;

import com.hospital.hospitalmanagement.userentity.User;
import com.hospital.hospitalmanagement.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // ✅ Constructor Injection (Preferred)
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Business logic to create user
    public User createUser(User user) {
        System.out.println("Service----------------------------" + user);
        return userRepository.save(user);
    }
    public ResponseEntity<?> updateUser(Long id, User incomingUser) {
        System.out.println("*****************userservice: LINE 30 **************************************" + id + "REQUEST BODY" );

        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        User existingUser = existingUserOpt.get();

        // Only update fields if new value is not null
        if (incomingUser.getFirstName() != null)
            existingUser.setFirstName(incomingUser.getFirstName());

        if (incomingUser.getLastName() != null)
            existingUser.setLastName(incomingUser.getLastName());

        if (incomingUser.getPhoneNumber() != null)
            existingUser.setPhoneNumber(incomingUser.getPhoneNumber());

        if (incomingUser.getRole() != null)
            existingUser.setRole(incomingUser.getRole());

        // Update timestamps
        existingUser.setUpdatedAt(LocalDateTime.now());

        // Save updated user
        User updatedUser = userRepository.save(existingUser);
        return ResponseEntity.ok(updatedUser);
    }



    public User getUser(User user) {
        System.out.println("Service----------------------------" + user);
        return userRepository.save(user);


    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByPhone(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }


}



