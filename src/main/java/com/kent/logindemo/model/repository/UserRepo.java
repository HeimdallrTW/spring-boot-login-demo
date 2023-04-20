package com.kent.logindemo.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kent.logindemo.model.bean.User;

public interface UserRepo extends JpaRepository<User, Integer> {

    // find user by email
    public User findByEmail(String email);
    
}
