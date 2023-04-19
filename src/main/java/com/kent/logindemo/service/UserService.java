package com.kent.logindemo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kent.logindemo.model.User;
import com.kent.logindemo.model.UserRepo;

@Service
public class UserService {
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // find optional user by email
    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userRepo.findByEmail(email));
    }

    public Optional<User> findUserById(int id) {
        return userRepo.findById(id);
    }
}
