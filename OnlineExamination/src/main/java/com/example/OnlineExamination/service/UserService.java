package com.example.OnlineExamination.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.OnlineExamination.model.User;
import com.example.OnlineExamination.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        return userRepository.save(user);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}

