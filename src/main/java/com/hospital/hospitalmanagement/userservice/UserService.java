package com.hospital.hospitalmanagement.userservice;
import com.hospital.hospitalmanagement.userentity.User;
import com.hospital.hospitalmanagement.userrepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        System.out.println("Service----------------------------"+ user);
        return userRepository.save(user);


    }

    public User getUser(User user) {
        System.out.println("Service----------------------------"+ user);
        return userRepository.save(user);


    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


}

